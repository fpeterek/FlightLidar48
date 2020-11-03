package org.fpeterek.flightlidar48.records;

public record Airport(
    String icao,
    String iata,
    String name,
    double lat,
    double lon,
    Country country
) {
}
