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

  private static JSONObject success() {
    return new JSONObject().put("status", "success");
  }

  private static JSONObject failure() {
    return new JSONObject().put("status", "failure");
  }

  public static String mapRecords(List<Flight> flights) {
    var arr = new JSONArray();
    flights.stream()
      .filter(fl -> fl.currentFlight() != null)
      .forEach(fl -> arr.put(mapRecord(fl)));
    return success().put("results", arr).toString();
  }

  public static String error(String reason) {
    return failure().put("reason", reason).toString();
  }

}
