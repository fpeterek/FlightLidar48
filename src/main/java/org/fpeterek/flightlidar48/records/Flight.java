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

  public Flight setDeparture(DateTime departure) {
    return new Flight(id, number, departure, arrival, originIcao, origin, destinationIcao, destination,
      aircraftRegistration, aircraft, currentFlightId, currentFlight);
  }

  public Flight setArrival(DateTime arrival) {
    return new Flight(id, number, departure, arrival, originIcao, origin, destinationIcao, destination,
      aircraftRegistration, aircraft, currentFlightId, currentFlight);
  }

  public Flight setAirports(Airport orig, Airport dest) {
    return new Flight(id, number, departure, arrival, orig.icao(), orig, dest.icao(), dest,
      aircraftRegistration, aircraft, currentFlightId, currentFlight);
  }

  public Flight setOrigin(Airport orig) {
    // I can't reuse setAirports() because destination could be null, which could lead to an NPE when setting
    // destinationIcao, which should be set every time, even if the airport wasn't fetched from the database
    return new Flight(id, number, departure, arrival, orig.icao(), orig, destinationIcao, destination,
      aircraftRegistration, aircraft, currentFlightId, currentFlight);
  }

  public Flight addAirport(Airport ap) {
    final var isOrigin = ap.icao().equals(originIcao);
    final var isDestination = ap.icao().equals(destinationIcao);

    if (isOrigin && isDestination) {
      return setAirports(ap, ap);
    }
    if (isOrigin) {
      return setOrigin(ap);
    }
    if (isDestination) {
      return setDestination(ap);
    }
    return this;
  }

  public Flight setDestination(Airport dest) {
    // Can't reuse setAirports for the same reason as above
    return new Flight(id, number, departure, arrival, originIcao, origin, dest.icao(), dest,
      aircraftRegistration, aircraft, currentFlightId, currentFlight);
  }

  public Flight setAircraft(Aircraft ac) {
    return new Flight(id, number, departure, arrival, originIcao, origin, destinationIcao, destination,
      ac.registration(), ac, currentFlightId, currentFlight);
  }

  public Flight setCurrentFlight(CurrentFlight cf) {
    return new Flight(id, number, departure, arrival, originIcao, origin, destinationIcao, destination,
      aircraftRegistration, aircraft, cf.id(), cf);
  }

}
