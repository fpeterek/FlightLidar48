package org.fpeterek.flightlidar48.records;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import java.util.List;

public record Aircraft(
    String registration,
    int msn,
    String airlineDesignator,
    Airline airline,
    String typeDesignator,
    AircraftType type,
    LocalDate firstFlight,
    List<Flight> flights) {

  int age() {
    final var now = DateTime.now();
    final var then = firstFlight.toDateTimeAtStartOfDay();
    final var diff = new Duration(then, now);
    return (int)(diff.getStandardDays() / 365);
  }

  public void addFlight(Flight fl) throws RuntimeException {
    if (!fl.aircraftRegistration().equals(registration)) {
      throw new RuntimeException("Flight was operated by '" + fl.aircraftRegistration() + "', not '" + registration + "'");
    }
    flights.add(fl);
  }

  public Aircraft addAirline(Airline al) {
    return new Aircraft(registration, msn, al.designator(), al, type.designator(), type, firstFlight, flights);
  }

  public Aircraft addType(AircraftType type) {
    return new Aircraft(registration, msn, airlineDesignator, airline, type.designator(), type, firstFlight, flights);
  }

}
