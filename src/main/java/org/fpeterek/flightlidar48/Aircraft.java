package org.fpeterek.flightlidar48;

public record Aircraft(
    String registration,
    AircrafType type,
    int age,
    Airline airline) {

}
