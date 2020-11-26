package org.fpeterek.flightlidar48.validator;

import org.fpeterek.flightlidar48.validator.validators.*;
import org.fpeterek.flightlidar48.validator.validators.messagevalidators.*;
import org.json.JSONException;
import org.json.JSONObject;


public class Validator {

  ReceiverDatabase receivers = new ReceiverDatabase();

  private final KafkaMessageValidator messageValidator = new KafkaMessageValidator();
  private final ReceiverValidator receiverValidator = new ReceiverValidator();
  private final KafkaWriter writer = new KafkaWriter();
  private final MailClient mailClient = new MailClient();

  private boolean validate(KafkaMessage msg) {
    return messageValidator.validate(msg);
  }

  private boolean validate(ReceiverData recv) {
    return receiverValidator.receiverInputAccepted(recv);
  }

  private void notify(ReceiverData recv) {
    if (mailClient.notifyReceiver(recv)) {
      recv.setNotified();
    }
  }

  private void notifyIfNeeded(ReceiverData recv) {
    if (recv.totalRequests() > Config.get().assessmentThreshold && recv.validPercentage() <= 50 && recv.isNotifiable()) {
      notify(recv);
    }
  }

  private void handle(String str) {

    final var json = new JSONObject(str);
    final var id = json.getInt("receiver");
    final var recv = receivers.get(id);

    var valid = false;

    try {
      KafkaMessage msg = KafkaMessage.fromJson(json);
      valid = validate(msg);
    } catch (JSONException ignored) {
      /* noop -> variable 'valid' stays false */
    } catch (Exception e) {
      e.printStackTrace();
      /* Variable 'valid' also stays false, however, exception could indicate an error, therefore we should at least */
      /* log the exception */
    }


    recv.addRequest(valid);
    notifyIfNeeded(recv);

    if (valid && validate(recv)) {
      writer.write(str);
    }

  }

  public void validate(String key, String value) {
    try {
      handle(value);
    } catch (JSONException ignored) {

    }
  }

}
