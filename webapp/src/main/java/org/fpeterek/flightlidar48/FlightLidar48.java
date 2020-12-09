package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;

import java.sql.SQLException;
import java.util.List;

public class FlightLidar48 {

  DataProxy dataproxy = new DataProxy();

  public FlightLidar48() throws SQLException { }

  public List<Flight> getFlights(GeoPoint lt, GeoPoint rb) throws SQLException {
    var low = new GeoPoint(Math.min(lt.lat(), rb.lat()), Math.min(lt.lon(), rb.lon()));
    var high = new GeoPoint(Math.max(lt.lat(), rb.lat()), Math.max(lt.lon(), rb.lon()));

    return dataproxy.getFlights(low, high);
  }

}
