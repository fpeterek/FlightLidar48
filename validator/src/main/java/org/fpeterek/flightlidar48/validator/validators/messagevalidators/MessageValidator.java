package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public abstract class MessageValidator {

  MessageValidator next = null;

  protected abstract boolean validateMessage(KafkaMessage msg);

  public void setNext(MessageValidator validator) {
    next = validator;
  }

  public boolean validate(KafkaMessage msg) {
    return validateMessage(msg) && (next == null || next.validate(msg));
  }

}
