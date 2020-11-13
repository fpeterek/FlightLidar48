package org.fpeterek.flightlidar48.records;

import org.joda.time.DateTime;

public record Flight(
    long id,
    String number,
    DateTime departure,
    DateTime arrival,
    String originIcao,
    Airport origin,
    String destinationIcao,
    Airport destination,
    String aircraftRegistration,
    Aircraft aircraft,
    long currentFlightId,
    CurrentFlight currentFlight
) {
}
