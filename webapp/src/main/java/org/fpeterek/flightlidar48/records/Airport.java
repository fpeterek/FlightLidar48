package org.fpeterek.flightlidar48.records;

public record Airport(
    String icao,
    String iata,
    String name,
    double lat,
    double lon,
    int countryId,
    Country country
) {
  public Airport addCountry(Country c) {
    return new Airport(icao, iata, name, lat, lon, c.id(), c);
  }
}
