package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class SquawkValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    return 0 < msg.squawk() && msg.squawk() < 10000;
  }

}
