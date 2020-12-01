package org.fpeterek.flightlidar48.json;

import org.json.JSONException;
import org.json.JSONObject;

public record KafkaMessage(
  int receiver,
  long timestamp,
  String flight,
  String aircraft,
  double lat,
  double lon,
  int squawk,
  int altitude,
  double direction,
  int speed,
  String orig,
  String dest
) {

  public static KafkaMessage fromJson(JSONObject json) throws JSONException {

    int reciever = json.getInt("receiver");
    long ts = json.getLong("timestamp");

    JSONObject data = json.getJSONObject("data");

    String flight = data.getString("number");
    String aircraft = data.getString("aircraft");
    double lat = data.getDouble("lat");
    double lon = data.getDouble("lon");
    int squawk = data.getInt("squawk");
    int altitude = data.getInt("altitude");
    double direction = data.getDouble("direction");
    int speed = data.getInt("speed");
    String orig = data.getString("origin");
    String dest = data.getString("destination");

    return new KafkaMessage(reciever, ts, flight, aircraft, lat, lon, squawk, altitude, direction, speed, orig, dest);

  }

}
