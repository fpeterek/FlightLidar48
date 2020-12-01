package org.fpeterek.flightlidar48.database.gateways;

import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

  public Flight withFlight(Flight fl) throws SQLException {
    // If flight hasn't yet departed, or has already arrived, there certainly won't be a 'current flight'
    if (fl.departure() == null || fl.arrival() != null) {
      return fl;
    }

    final var query = baseQuery() + " WHERE flight=?;";
    var stmt = conn.prepareStatement(query);
    stmt.setLong(1, fl.id());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      fl = fl.setCurrentFlight(extractOne(rs).addFlight(fl));
    }
    return fl;

  }

  public void setFlights(List<Flight> flights) throws SQLException {
    for (int i = 0; i < flights.size(); ++i) {
      flights.set(i, withFlight(flights.get(i)));
    }
  }

  public List<CurrentFlight> get() throws SQLException {
    return (List)getAll();
  }

  public void removeLanded() throws SQLException {
    final var query = "DELETE FROM current_flight USING flight WHERE current_flight.flight=flight.id AND flight.arrival IS NOT NULL;";
    var stmt = conn.prepareStatement(query);
    stmt.executeUpdate();
  }

  public List<CurrentFlight> fetchAirborne() throws SQLException {

    final var query = baseQuery() + " JOIN flight ON current_flight.flight=flight.id WHERE flight.arrival IS NULL;";
    var stmt = conn.prepareStatement(query);
    var rs = stmt.executeQuery();
    var list = new ArrayList<CurrentFlight>();

    while (rs.next()) {
      list.add(extractOne(rs));
    }

    return list;
  }

  public void delete(long id) throws SQLException {

    final var query = "DELETE FROM current_flight WHERE id=?;";
    var stmt = conn.prepareStatement(query);
    stmt.setLong(1, id);
    stmt.executeUpdate();

  }

}
