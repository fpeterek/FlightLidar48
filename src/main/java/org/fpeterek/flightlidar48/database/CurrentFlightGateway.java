package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Country;
import org.fpeterek.flightlidar48.records.CurrentFlight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CurrentFlightGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT id, flight, lat, lon, squawk, altitude, direction, groundspeed FROM current_flight";
  }

  public CurrentFlightGateway() throws SQLException {
    super();
  }

  public CurrentFlightGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected CurrentFlight extractOne(ResultSet rs) throws SQLException {
    final var id = rs.getLong("id");
    final var flight = rs.getLong("flight");
    final var lat = rs.getDouble("lat");
    final var lon = rs.getDouble("lon");
    final var squawk = rs.getInt("squawk");
    final var altitude = rs.getInt("altitude");
    final var direction = rs.getDouble("direction");
    final var groundspeed = rs.getInt("groundspeed");

    return new CurrentFlight(id, flight, null, lat, lon, squawk, altitude, direction, groundspeed);
  }

  public List<CurrentFlight> get() throws SQLException {
    return (List)getAll();
  }
}
