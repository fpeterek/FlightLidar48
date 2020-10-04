package org.fpeterek.flightlidar48;

public record Flight(
        String number,
        Airport origin,
        Airport destination,
        Aircraft aircraft,
        double lat,
        double lon,
        int squawk,
        int groundspeed,
        int altitude) {
}
