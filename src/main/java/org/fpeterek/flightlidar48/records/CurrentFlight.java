package org.fpeterek.flightlidar48.records;

public record CurrentFlight(
    long id,
    long flightId,
    Flight flight,
    double lat,
    double lon,
    int squawk,
    int altitude,
    double direction,
    int groundspeed
) {
}
