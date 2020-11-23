package org.fpeterek.flightlidar48.validator.validators.messagevalidators;

import org.fpeterek.flightlidar48.validator.KafkaMessage;

public class KafkaMessageValidator extends MessageValidator {

  public KafkaMessageValidator() {

    var ap = new AirportValidator();

    var speed = new SpeedValidator();
    speed.setNext(ap);

    var dir = new DirectionValidator();
    dir.setNext(speed);

    var alt = new AltitudeValidator();
    alt.setNext(dir);

    var squawk = new SquawkValidator();
    squawk.setNext(alt);

    var latlon = new LatLonValidator();
    latlon.setNext(squawk);

    var ac = new AircraftValidator();
    ac.setNext(latlon);

    var fl = new FlightValidator();
    fl.setNext(ac);

    var ts = new TimestampValidator();
    ts.setNext(fl);

    setNext(ts);

  }

  @Override
  protected boolean validateMessage(KafkaMessage msg) {
    return true;
  }
}
