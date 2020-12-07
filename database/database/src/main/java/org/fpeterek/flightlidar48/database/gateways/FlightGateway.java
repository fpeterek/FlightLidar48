package org.fpeterek.flightlidar48.database.gateways;

import org.fpeterek.flightlidar48.database.records.Aircraft;
import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT id, number, departure, arrival, origin, destination, aircraft, current_flight FROM flight";
  }

  public FlightGateway() throws SQLException {
    super();
  }

  public FlightGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected Flight extractOne(ResultSet rs) throws SQLException {
    final var id = rs.getLong("id");
    final var number = rs.getString("number");
    final var departure = rs.getTimestamp("departure");
    final var arrival = rs.getTimestamp("arrival");
    final var origin = rs.getString("origin");
    final var destination = rs.getString("destination");
    final var aircraft = rs.getString("aircraft");
    final var currentFlight = rs.getLong("current_flight");

    final var jodaDeparture = departure == null ? null : new DateTime(departure);
    final var jodaArrival = arrival == null ? null : new DateTime(arrival);

    return new Flight(
      id, number, jodaDeparture, jodaArrival, origin, null, destination, null,
      aircraft, null, currentFlight, null
    );
  }

  public void flightsFor(Aircraft aircraft) throws SQLException, RuntimeException {
    final var query = baseQuery() + " WHERE aircraft = ?;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, aircraft.registration());

    var rs = stmt.executeQuery();

    while (rs.next()) {
      aircraft.addFlight(extractOne(rs).setAircraft(aircraft));
    }

  }

  public void flightsForAll(List<Aircraft> aircraft) throws SQLException, RuntimeException {
    for (var ac : aircraft) {
      flightsFor(ac);
    }
  }

  public CurrentFlight withFlight(CurrentFlight cf) throws SQLException {

    final var query = baseQuery() + " WHERE current_flight=?;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setLong(1, cf.id());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      cf = cf.addFlight(extractOne(rs).setCurrentFlight(cf));
    }
    return cf;

  }

  public void setFlights(List<CurrentFlight> flights) throws SQLException {
    for (int i = 0; i < flights.size(); ++i) {
      flights.set(i, withFlight(flights.get(i)));
    }
  }

  public List<Flight> get() throws SQLException {
    return (List)getAll();
  }

  public void landFlight(Flight flight) throws SQLException {

    final var query = "UPDATE flight SET arrival=current_timestamp WHERE flight.id = ?;";
    var stmt = conn.prepareStatement(query);
    stmt.setLong(1, flight.id());
    stmt.executeUpdate();

  }

  public Flight fetchLatest(String flId) throws SQLException {

    final var query = baseQuery() + " WHERE number = ? AND arrival IS NULL ORDER BY departure DESC LIMIT 1;";
    var stmt = conn.prepareStatement(query);
    stmt.setString(1, flId);
    var rs = stmt.executeQuery();

    if (!rs.next()) {
      return null;
    }
    return extractOne(rs);

  }

  public List<Flight> fetchAirborne() throws SQLException {

    final var query = baseQuery() + " WHERE arrival IS NULL;";
    var stmt = conn.prepareStatement(query);
    var rs = stmt.executeQuery();
    var list = new ArrayList<Flight>();

    while (rs.next()) {
      list.add(extractOne(rs));
    }

    return list;
  }

  public void createFlight(String number, String orig, String dest, String aircraft) throws SQLException {

    final var query = "INSERT INTO flight (number, departure, origin, destination, aircraft) " +
      "VALUES(?, current_timestamp, ?, ?, ?);";
    var stmt = conn.prepareStatement(query);

    stmt.setString(1, number);
    stmt.setString(2, orig);
    stmt.setString(3, dest);
    stmt.setString(4, aircraft);
    stmt.executeUpdate();

  }

}
