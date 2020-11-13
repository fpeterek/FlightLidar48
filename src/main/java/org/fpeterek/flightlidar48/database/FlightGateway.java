package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Flight;
import org.joda.time.DateTime;

import java.sql.ResultSet;
import java.sql.SQLException;
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

  public List<Flight> get() throws SQLException {
    return (List)getAll();
  }
}
