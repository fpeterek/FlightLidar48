package org.fpeterek.flightlidar48.util;

public record GeoPoint(
  double lat,
  double lon
) {
  public double y() {
    return lat;
  }
  public double x() {
    return lon;
  }
}
