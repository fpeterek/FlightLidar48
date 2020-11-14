package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.AircraftType;
import org.fpeterek.flightlidar48.records.Aircraft;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fpeterek.flightlidar48.records.Airline;
import org.fpeterek.flightlidar48.records.Flight;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class AircraftGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT registration, msn, airline, type_designator, first_flight FROM aircraft";
  }

  public AircraftGateway() throws SQLException {
    super();
  }

  public AircraftGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected Aircraft extractOne(ResultSet rs) throws SQLException {
    final var registration  = rs.getString("registration");
    final var msn  = rs.getInt("msn");
    final var airline  = rs.getString("airline");
    final var type  = rs.getString("type");
    final var firstFlight  = new LocalDate(rs.getDate("first_flight"));

    return new Aircraft(registration, msn, airline, null, type, null, firstFlight, new ArrayList<>());
  }

  public List<AircraftType> get() throws SQLException {
    return (List)getAll();
  }


  public Flight withAircraft(Flight fl) throws SQLException {

    final var query = baseQuery() + " WHERE registration=%;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, fl.aircraftRegistration());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      fl = fl.setAircraft(extractOne(rs));
    }
    return fl;

  }

  public void setAircraft(List<Flight> flights) throws SQLException {
    for (int i = 0; i < flights.size(); ++i) {
      flights.set(i, withAircraft(flights.get(i)));
    }
  }

  public Airline addFleet(Airline airline) throws SQLException {

    final var query = baseQuery() + " WHERE airline=%;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, airline.designator());

    var rs = stmt.executeQuery();

    while (rs.next()) {
      airline.addToFleet(extractOne(rs));
    }

    return airline;
  }

  public void setFleets(List<Airline> airlines) throws SQLException {
    for (var airline : airlines) {
      addFleet(airline);
    }
  }

}
