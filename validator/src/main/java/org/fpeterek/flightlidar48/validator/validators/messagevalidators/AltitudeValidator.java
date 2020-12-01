package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.json.KafkaMessage;

public class AltitudeValidator extends MessageValidator {

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    // We don't intend to track submarines or rockets, at least not for now
    // However, we need to leave room for supersonic aircraft, balloons, and for normal aircraft
    // landing at airports located below sea level (probably only a couple hundred feet at most, but I want to leave
    // some room)
    return -3000 < msg.altitude() && msg.altitude() < 1000000;
  }

}
