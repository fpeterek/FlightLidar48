package org.fpeterek.flightlidar48;

public record Airport(
        String icao,
        String iata,
        String name,
        String city
) {
}
