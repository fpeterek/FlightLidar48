package org.fpeterek.flightlidar48.writer.data;

public record BookKeeperRecord(
  boolean lockAcquired,
  FlightData flightData
) {

  public static BookKeeperRecord failure = new BookKeeperRecord(false, null);

  public static BookKeeperRecord success(FlightData fd) {
    return new BookKeeperRecord(true, fd);
  }


}
