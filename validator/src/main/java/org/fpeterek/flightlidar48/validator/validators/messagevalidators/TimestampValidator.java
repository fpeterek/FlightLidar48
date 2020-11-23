package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.validator.KafkaMessage;

public class TimestampValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    /* Receiver is hardly going to send data from the future                */
    /* We're assuming all receivers use the same timezone, preferably GMT+0 */
    return msg.timestamp() <= System.currentTimeMillis();
  }

}
