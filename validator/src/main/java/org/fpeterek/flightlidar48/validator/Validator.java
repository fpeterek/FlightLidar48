package org.fpeterek.flightlidar48.validator;

import org.fpeterek.flightlidar48.validator.validators.*;
import org.fpeterek.flightlidar48.validator.validators.messagevalidators.*;
import org.json.JSONException;
import org.json.JSONObject;


public class Validator {

  ReceiverDatabase receivers = new ReceiverDatabase();

  private final KafkaMessageValidator messageValidator = new KafkaMessageValidator();
  private final ReceiverValidator receiverValidator = new ReceiverValidator();

  private boolean validate(KafkaMessage msg) {
    return messageValidator.validate(msg);
  }

  private boolean validate(ReceiverData recv) {
    return receiverValidator.receiverInputAccepted(recv);
  }

  private void handle(JSONObject json) {

    final var id = json.getInt("receiver");
    final var recv = receivers.get(id);

    var valid = false;

    try {
      KafkaMessage msg = KafkaMessage.fromJson(json);
      valid = validate(msg);
    } catch (Exception ignored) {
      /* noop -> valid stays false */
    }

    recv.addRequest(valid);

    if (valid && validate(recv)) {
      /* TODO: Implement */
      System.out.println(json);
    }

  }

  public void validate(String key, String value) {
    try {
      var json = new JSONObject(value);
      handle(json);
    } catch (JSONException ignored) {

    }
  }

}
