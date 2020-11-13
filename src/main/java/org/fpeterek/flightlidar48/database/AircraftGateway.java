package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.AircrafType;
import org.fpeterek.flightlidar48.records.Aircraft;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.LocalDate;
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

    return new Aircraft(registration, msn, airline, null, type, null, firstFlight, null);
  }

  public List<AircrafType> get() throws SQLException {
    return (List)getAll();
  }
}
