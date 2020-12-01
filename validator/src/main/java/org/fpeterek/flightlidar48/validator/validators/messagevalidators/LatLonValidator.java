package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class LatLonValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    return -90.0 <= msg.lat() && msg.lat() <= 90.0 && 0.0 <= msg.lon() && msg.lon() <= 360.0;
  }

}
