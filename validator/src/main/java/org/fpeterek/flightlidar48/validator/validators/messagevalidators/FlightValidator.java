package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class FlightValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    /* Could be done using regex */

    final var flight = msg.flight();

    if (flight.length() > 6) {
      return false;
    }
    var alpha = Character.isAlphabetic(flight.charAt(0)) && Character.isAlphabetic(flight.charAt(1));
    var nums = flight.length() > 2;

    for (int i = 2; i < flight.length(); ++i) {
      if (!Character.isDigit(flight.charAt(i))) {
        nums = false;
        break;
      }
    }

    return alpha && nums;
  }

}
