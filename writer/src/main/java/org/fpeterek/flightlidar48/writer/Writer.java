package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.database.gateways.FlightGateway;
import org.fpeterek.flightlidar48.json.KafkaMessage;
import org.json.JSONObject;

import java.sql.SQLException;

public class Writer {

  private FlightGateway flgw = new FlightGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);

  public Writer() throws SQLException {
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

  private void handleFlightRecord(KafkaMessage message) {

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
