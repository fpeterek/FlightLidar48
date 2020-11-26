package org.fpeterek.flightlidar48.database.gateways;

import org.fpeterek.flightlidar48.database.records.Airport;
import org.fpeterek.flightlidar48.database.records.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CountryGateway extends Gateway {

  @Override
  protected final String baseQuery() {
    return "SELECT id, country_name, airport_prefix, registration_prefix FROM country";
  }

  public CountryGateway() throws SQLException {
    super();
  }

  public CountryGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  protected Country extractOne(ResultSet rs) throws SQLException {
    final var id = rs.getInt("id");
    final var name = rs.getString("country_name");
    final var airportPrefix = rs.getString("airport_prefix");
    final var aircraftPrefix = rs.getString("registration_prefix");

    return new Country(id, name, airportPrefix, aircraftPrefix);
  }

  public List<Country> get() throws SQLException {
    return (List)getAll();
  }

  public Airport withCountry(Airport ap) throws SQLException {

    final var query = baseQuery() + " WHERE id=?;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setLong(1, ap.countryId());

    var rs = stmt.executeQuery();

    if (rs.next()) {
      ap = ap.addCountry(extractOne(rs));
    }
    return ap;

  }

  public void setCountries(List<Airport> airports) throws SQLException {
    for (int i = 0; i < airports.size(); ++i) {
      airports.set(i, withCountry(airports.get(i)));
    }
  }

}
