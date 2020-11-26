package org.fpeterek.flightlidar48.database.records;

import org.json.JSONObject;

public record Country(
    int id,
    String name,
    String airportPrefix, // eg. LK for the Czech Republic, ED for Germany, etc...
    String registrationPrefix // OK for the Czech Republic, HB for Switzerland, etc...
) {

  @Override
  public String toString() {
    return json().toString();
  }

  public JSONObject json() {
    return new JSONObject()
      .put("country", name)
      .put("airportPrefix", airportPrefix)
      .put("registrationPrefix", registrationPrefix);
  }

}
