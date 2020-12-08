package org.fpeterek.flightlidar48.writer.data;

import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;

public class FlightData {

  private Flight _flight;
  private CurrentFlight _current;
  private long lastModified = System.currentTimeMillis();

  public FlightData(Flight fl, CurrentFlight cf) {
    _flight = fl;
    _current = cf;
  }

  public Flight flight() {
    return _flight;
  }

  public CurrentFlight current() {
    return _current;
  }

  public void setFlight(Flight flight) {
    _flight = flight;
    lastModified = System.currentTimeMillis();
  }

  public void setCurrent(CurrentFlight curr) {
    _current = curr;
    lastModified = System.currentTimeMillis();
  }

  public long timestamp() {
    return lastModified;
  }

}
