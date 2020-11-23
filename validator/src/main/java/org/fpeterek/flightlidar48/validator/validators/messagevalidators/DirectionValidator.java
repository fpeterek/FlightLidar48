package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.validator.KafkaMessage;

public class DirectionValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    return 0.0 < msg.direction() && msg.direction() <= 360.0;
  }

}
