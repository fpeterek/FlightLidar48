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
    AircrafType type,
    LocalDate firstFlight,
    List<Flight> flights) {

  int age() {
    final var now = DateTime.now();
    final var then = firstFlight.toDateTimeAtStartOfDay();
    final var diff = new Duration(then, now);
    return (int)(diff.getStandardDays() / 365);
  }

}
