package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.AircraftType;
import org.fpeterek.flightlidar48.records.Aircraft;

import java.sql.PreparedStatement;
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

  protected AircraftType extractOne(ResultSet rs) throws SQLException {
    final var designator  = rs.getString("designator");
    final var manufacturer = rs.getString("manufacturer");
    final var family = rs.getString("family");
    final var subtype = rs.getString("subtype");

    return new AircraftType(designator, manufacturer, family, subtype);
  }

  public List<AircraftType> get() throws SQLException {
    return (List)getAll();
  }

  public Aircraft withType(Aircraft ac) throws SQLException {

    final var query = baseQuery() + " WHERE designator=?;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, ac.typeDesignator());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      ac = ac.addType(extractOne(rs));
    }
    return ac;

  }

  public void setTypes(List<Aircraft> aircraft) throws SQLException {
    for (int i = 0; i < aircraft.size(); ++i) {
      aircraft.set(i, withType(aircraft.get(i)));
    }
  }

}
