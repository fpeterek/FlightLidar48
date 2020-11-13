package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Airline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AirlineGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT designator, airline_name FROM airline";
  }

  public AirlineGateway() throws SQLException {
    super();
  }

  public AirlineGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected Airline extractOne(ResultSet rs) throws SQLException {
    final var designator  = rs.getString("designator");
    final var name = rs.getString("airline_name");

    return new Airline(designator, name, null);
  }

  public List<Airline> get() throws SQLException {
    return (List)getAll();
  }

}
