package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.validator.KafkaMessage;

public class AirportValidator extends MessageValidator {

  private boolean airportValidation(String airport) {
    if (airport.length() != 4) {
      return false;
    }

    var alphas = true;

    for (int i = 0; i < airport.length(); ++i) {
      char c = airport.charAt(i);
      if (!(Character.isAlphabetic(c) && Character.isUpperCase(c))) {
        alphas = false;
        break;
      }
    }

    return alphas;

  }

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    return airportValidation(msg.orig()) && airportValidation(msg.dest());
  }

}
