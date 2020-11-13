package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.AircrafType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AircraftTypeGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT designator, manufacturer, family, subtype FROM aircraft_type";
  }

  public AircraftTypeGateway() throws SQLException {
    super();
  }

  public AircraftTypeGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected AircrafType extractOne(ResultSet rs) throws SQLException {
    final var designator  = rs.getString("designator");
    final var manufacturer = rs.getString("manufacturer");
    final var family = rs.getString("family");
    final var subtype = rs.getString("subtype");

    return new AircrafType(designator, manufacturer, family, subtype);
  }

  public List<AircrafType> get() throws SQLException {
    return (List)getAll();
  }

}
