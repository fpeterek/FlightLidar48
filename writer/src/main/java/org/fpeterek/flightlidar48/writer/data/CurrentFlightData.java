package org.fpeterek.flightlidar48.writer.data;

import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.json.KafkaMessage;

public class CurrentFlightData {
  public CurrentFlight flight;
  public long timestamp;

  public static void fromKafka(KafkaMessage msg) {
  }
}
