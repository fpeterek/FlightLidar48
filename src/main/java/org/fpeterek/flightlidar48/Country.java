package org.fpeterek.flightlidar48;

public record Country(
    int id,
    int name,
    String airportPrefix, // eg. LK for the Czech Republic, ED for Germany, etc...
    String registrationPrefix // OK for the Czech Republic, HB for Switzerland, etc...
) {
}
