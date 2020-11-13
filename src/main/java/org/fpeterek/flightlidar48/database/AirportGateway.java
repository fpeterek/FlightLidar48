package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Airport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AirportGateway extends Gateway {
  @Override
  protected final String baseQuery() {
    return "SELECT icao, iata, airport_name, lat, lon, country_id FROM airport";
  }

  public AirportGateway() throws SQLException {
    super();
  }

  public AirportGateway(String url, String user, String password) throws SQLException {
    super(url, user, password);
  }

  @Override
  protected Airport extractOne(ResultSet rs) throws SQLException {
    final var icao = rs.getString("icao");
    final var iata = rs.getString("iata");
    final var name = rs.getString("airport_name");
    final var lat = rs.getDouble("lat");
    final var lon = rs.getDouble("lon");
    final var countryId = rs.getInt("country_id");

    return new Airport(icao, iata, name, lat, lon, countryId, null);
  }

  public List<Airport> get() throws SQLException {
    return (List)getAll();
  }

}
