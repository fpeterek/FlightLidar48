package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Aircraft;
import org.fpeterek.flightlidar48.database.records.Airport;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JsonFormatter {

  private static JSONObject mapRecordObject(Flight flight) {

    if (flight == null) {
      return null;
    }

    return new JSONObject()
      .put("number", flight.number())
      .put("departure", flight.departure().toString())
      .put("origin", flight.originIcao())
      .put("destination", flight.destinationIcao())
      .put("aircraft", flight.aircraftRegistration())
      .put("lat", flight.currentFlight().lat())
      .put("lon", flight.currentFlight().lon())
      .put("squawk", flight.currentFlight().squawk())
      .put("altitude", flight.currentFlight().altitude())
      .put("direction", flight.currentFlight().direction())
      .put("speed", flight.currentFlight().groundspeed());
  }

  private static JSONObject aircraftData(Aircraft aircraft) {
    if (aircraft == null) {
      return null;
    }

    return new JSONObject()
      .put("registration", aircraft.registration())
      .put("msn", aircraft.msn())
      .put("airline", aircraft.airlineDesignator())
      .put("type", aircraft.typeDesignator())
      .put("age", aircraft.age());
  }

  private static JSONObject airportData(Airport airport) {
    if (airport == null) {
      return null;
    }

    return new JSONObject()
      .put("icao", airport.icao())
      .put("iata", airport.iata())
      .put("name", airport.name());
  }

  private static JSONObject searchResultObject(SearchResult sr) {

    if (sr == null) {
      return null;
    }

    return new JSONObject()
      .put("flight", mapRecordObject(sr.flight()))
      .put("aircraft", aircraftData(sr.aircraft()))
      .put("airport", airportData(sr.airport()));
  }

  public static String searchResult(SearchResult sr) {
    return success().put("tracked", searchResultObject(sr)).toString();
  }

  private static JSONObject success() {
    return new JSONObject().put("status", "success");
  }

  private static JSONObject failure() {
    return new JSONObject().put("status", "failure");
  }

  public static String mapRecords(List<Flight> flights, SearchResult sr) {
    var arr = new JSONArray();
    flights.stream()
      .filter(fl -> fl.currentFlight() != null)
      .forEach(fl -> arr.put(mapRecordObject(fl)));
    return success()
      .put("results", arr)
      .put("tracked", searchResultObject(sr))
      .toString();
  }

  public static String error(String reason) {
    return failure().put("reason", reason).toString();
  }

}
