package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.json.KafkaMessage;
import org.fpeterek.flightlidar48.writer.data.FlightData;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Writer {

  private final DataProxy dataproxy = new DataProxy();

  private final List<FlightData> flights = new ArrayList<>();

  public Writer() throws SQLException {
  }

  private FlightData findFlight(String flight) {
    return flights.stream().filter(fl -> fl.flight().number().equals(flight)).findFirst().orElse(null);
  }

  private void clock() {
    /* noop */

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
  }

  private void createFlight(KafkaMessage msg) {

    try {
      var fl = dataproxy.createAndGetFlight(msg.flight(), msg.orig(), msg.dest(), msg.aircraft());

      dataproxy.createCurrent(
        fl.id(), msg.lat(), msg.lon(), msg.squawk(), msg.altitude(), msg.direction(), msg.speed()
      );
      dataproxy.withCurrent(fl);

      var current = fl.currentFlight();

      if (current == null) {
        throw new RuntimeException("Failed to fetch current flight for flight " + fl.number());
      }

      flights.add(new FlightData(fl, current));

    } catch (Exception ex) {
      /* Just log the failure and try again on next message */
      System.out.println("Failed to create flight '" + msg.flight() + "' with exception " + ex.getClass());
      System.out.println(ex.getMessage());
    }

  }

  private void handleFlightRecord(KafkaMessage message) {

    var flightData = findFlight(message.flight());

    if (flightData == null) {
      createFlight(message);
      return;
    }

    // TODO: update flight

  }

  public void handleMessage(String key, String value) {

    var json = new JSONObject(value);

    if (json.has("healthcheck")) {
      clock();
    } else {
      handleFlightRecord(KafkaMessage.fromJson(json));
    }

  }

}
