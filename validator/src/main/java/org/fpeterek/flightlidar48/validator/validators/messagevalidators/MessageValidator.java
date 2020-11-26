package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.validator.KafkaMessage;

public abstract class MessageValidator {

  MessageValidator next = null;

  protected abstract boolean validateMessage(KafkaMessage msg);

  public void setNext(MessageValidator validator) {
    next = validator;
  }

  public boolean validate(KafkaMessage msg) {
    System.out.println(this.getClass().getName());
    return validateMessage(msg) && (next == null || next.validateMessage(msg));
  }

}
