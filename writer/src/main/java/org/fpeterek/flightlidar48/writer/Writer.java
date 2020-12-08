package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.json.KafkaMessage;
import org.fpeterek.flightlidar48.writer.data.BookKeeper;
import org.fpeterek.flightlidar48.writer.data.FlightData;
import org.json.JSONObject;

import java.sql.SQLException;

public class Writer {

  private final DataProxy dataproxy = new DataProxy();
  private final BookKeeper bookkeeper = new BookKeeper(dataproxy.fetchFlights());
  private final DeletionTracker tracker = new DeletionTracker();

  public Writer() throws SQLException { }

  private void clock() throws SQLException {
    /* The idea is that we should send requests to DB in batches                */
    /* Clock input would be good for synchronization, batches should be         */
    /* sent whenever a certain threshold is met                                 */
    /* Either the batch has reached a certain size, or we haven't updated       */
    /* the DB in a certain time (we want our data to be up to date,             */
    /* we don't want to keep data in memory for two minutes without updating DB */
    /* because there's very little traffic                                      */
    /* A periodic healthcheck to check that we a) have connection to Kafka      */
    /* and b) send data periodically would be useful                            */
    /* However, the database library currently doesn't support batch requests   */
    /* Meaning this function can't do much as of now                            */

    if (tracker.status()) {
      var old = bookkeeper.removeOld(System.currentTimeMillis() - Config.get().deletionThresholdMs);
      dataproxy.landFlights(old);
    }

  }

  private Flight createCurrentAndUpdate(Flight fl, KafkaMessage msg) throws SQLException {
    dataproxy.createCurrent(
      fl.id(), msg.lat(), msg.lon(), msg.squawk(), msg.altitude(), msg.direction(), msg.speed()
    );
    return dataproxy.withCurrent(fl);
  }

  private void createFlight(KafkaMessage msg) {

    try {
      var fl = dataproxy.createAndGetFlight(msg.flight(), msg.orig(), msg.dest(), msg.aircraft());
      fl = createCurrentAndUpdate(fl, msg);
      var current = fl.currentFlight();

      if (current == null) {
        throw new RuntimeException("Failed to fetch current flight for flight " + fl.number());
      }

      bookkeeper.put(msg.flight(), new FlightData(fl, current));

    } catch (Exception ex) {
      /* Just log the failure and try again on next message */
      System.out.println("Failed to create flight '" + msg.flight() + "' with exception " + ex.getClass());
      System.out.println(ex.getMessage());
    }
  }

  private CurrentFlight updatedFlight(CurrentFlight cf, KafkaMessage msg) {
    return new CurrentFlight(
      cf.id(), cf.flightId(), cf.flight(), msg.lat(),
      msg.lon(), msg.squawk(), msg.altitude(), msg.direction(), msg.speed()
    );
  }

  private Flight withUpdatedCurrent(Flight flight, KafkaMessage msg) throws SQLException {
    if (flight.currentFlight() == null) {
      flight = createCurrentAndUpdate(flight, msg);
    } else {
      flight = flight.setCurrentFlight(updatedFlight(flight.currentFlight(), msg));
      dataproxy.updateCurrent(flight.currentFlight());
    }

    return flight;
  }

  private void updateFlight(FlightData flightData, KafkaMessage msg) throws SQLException {
    if (msg.timestamp() <= flightData.timestamp()) {
      return;
    }

    flightData.setFlight(
      withUpdatedCurrent(flightData.flight(), msg)
    );
  }

  private void handleMessage(FlightData flightData, KafkaMessage message) throws SQLException {
    if (flightData == null) {
      createFlight(message);
    } else {
      updateFlight(flightData, message);
    }
  }

  private void tryLock(KafkaMessage message) throws SQLException {

    try {
      var bkrecord = bookkeeper.acquire(message.flight());

      if (!bkrecord.lockAcquired()) {
        return;
      }

      handleMessage(bkrecord.flightData(), message);
    } finally {
      bookkeeper.release(message.flight());
    }
  }

  public void tryHandle(JSONObject json) throws Exception {

    if (json.has("healthcheck")) {
      clock();
    } else {
      tryLock(KafkaMessage.fromJson(json));
    }

  }

  public void handleMessage(String key, String value) {

    try {
      tryHandle(new JSONObject(value));
    } catch (Exception e) {
      System.out.println("Failed to write item with exception " + e.getClass());
      e.printStackTrace();
    }
  }
}
