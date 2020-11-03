package org.fpeterek.flightlidar48;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;

public record Aircraft(
    String registration,
    int msn,
    Airline airline,
    AircrafType type,
    DateTime firstFlight,
    List<Flight> flights) {

  int age() {
    final var now = DateTime.now();
    final var diff = new Duration(firstFlight, now);
    return (int)(diff.getStandardDays() / 365);
  }

}
