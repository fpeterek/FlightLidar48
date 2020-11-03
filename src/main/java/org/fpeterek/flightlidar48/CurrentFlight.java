package org.fpeterek.flightlidar48;

public record CurrentFlight(
    int id,
    Flight flight,
    double lat,
    double lon,
    int squawk,
    int altitude,
    double direction,
    int groundspeed
) {
}
