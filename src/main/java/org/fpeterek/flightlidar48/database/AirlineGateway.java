package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Aircraft;
import org.fpeterek.flightlidar48.records.Airline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    return new Airline(designator, name, new ArrayList<>());
  }

  public List<Airline> get() throws SQLException {
    return (List)getAll();
  }

  public Aircraft withAirline(Aircraft ac) throws SQLException {

    final var query = baseQuery() + " WHERE designator=?;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, ac.airlineDesignator());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      ac = ac.addAirline(extractOne(rs));
    }
    return ac;

  }

  public void setTypes(List<Aircraft> aircraft) throws SQLException {
    for (int i = 0; i < aircraft.size(); ++i) {
      aircraft.set(i, withAirline(aircraft.get(i)));
    }
  }

}
