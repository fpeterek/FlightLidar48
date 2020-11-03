package org.fpeterek.flightlidar48;

import org.joda.time.DateTime;

public record Flight(
    int id,
    String number,
    DateTime departure,
    DateTime arrival,
    Airport origin,
    Airport destination,
    Aircraft aircraft,
    CurrentFlight currentFlight
) {
}
