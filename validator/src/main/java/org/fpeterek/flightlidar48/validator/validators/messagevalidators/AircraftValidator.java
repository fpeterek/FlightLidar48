package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class AircraftValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {

    final var aircraft = msg.aircraft();

    if (aircraft.length() != 6) {
      return false;
    }

    var dash = aircraft.charAt(1) == '-' || aircraft.charAt(2) == '-';

    var dashes = 0;
    var alnums = true;

    for (int i = 0; i < aircraft.length(); ++i) {
      char c = aircraft.charAt(i);

      if (c == '-') {
        ++dashes;
      } else if (!(Character.isAlphabetic(c) || Character.isDigit(c))) {
        alnums = false;
      }
    }

    return dash && dashes == 1 && alnums;
  }

}
