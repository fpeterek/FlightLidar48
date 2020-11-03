package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Country;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryGateway {

  private final Connection conn;
  private final String baseQuery = "SELECT id, country_name, airport_prefix, registration_prefix FROM country";

  public CountryGateway() throws SQLException {
    conn = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/flightlidar",
      "fpeterek",
      ""
    );
  }

  public CountryGateway(String url, String user, String password) throws SQLException {
    conn = DriverManager.getConnection(url, user, password);
  }

  private Country extractOne(ResultSet rs) throws SQLException {
    final var id = rs.getInt("id");
    final var name = rs.getString("country_name");
    final var airportPrefix = rs.getString("airport_prefix");
    final var aircraftPrefix = rs.getString("registration_prefix");

    return new Country(id, name, airportPrefix, aircraftPrefix);
  }

  public List<Country> get() throws SQLException {

    final var stmt = conn.createStatement();
    final var res = new ArrayList<Country>();
    final var sql = baseQuery + ";";

    var set = stmt.executeQuery(sql);

    while(set.next()) {
      res.add(extractOne(set));
    }

    return res;
  }

}
