package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Flight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class JsonFormatter {

  private static JSONObject mapRecord(Flight flight) {
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

  public static String mapRecords(List<Flight> flights) {
    var arr = new JSONArray();
    flights.stream()
      .filter(fl -> fl.currentFlight() != null)
      .forEach(fl -> arr.put(mapRecord(fl)));
    return arr.toString();
  }

}
