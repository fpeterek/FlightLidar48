package org.fpeterek.flightlidar48.javaapp;

public class Aircraft {

  private double latitude;
  private double longitude;
  final double direction;
  final int speed;
  final String registration;

  public Aircraft(double lat, double lon, double dir, int velocity, String reg) {
    latitude = lat;
    longitude = lon;
    direction = dir;
    speed = velocity;
    registration = reg;
  }

  public double lat() {
    return latitude;
  }

  public double lon() {
    return longitude;
  }

  private double adjustLat(double lat) {
    if (lat < -90) {
      return lat + 180.0;
    } else if (lat > 90) {
      return lat - 180.0;
    }
    return lat;
  }

  private double adjustLon(double lon) {
    if (lon < 0) {
      return lon + 360.0;
    } else if (lon > 360) {
      return lon - 360.0;
    }
    return lon;
  }

  public void calcEstimate(double dt) {
    final var mps = (speed * 1.856) / 3.6;
    final var dir = Math.toRadians(direction);
    final var dx = mps * dt * Math.cos(dir);
    final var dy = mps * dt * Math.cos(dir);

    final var deltaLatitude = dy / 6378000 * 180 * Math.PI;
    final var newLat = adjustLat(latitude + deltaLatitude);

    final var deltaLongitude = (dx / 6378000 * 180 * Math.PI) / Math.cos(Math.toRadians(newLat));
    final var newLon = adjustLon(longitude + deltaLongitude);

    latitude = newLat;
    longitude = newLon;
  }

}
