package org.fpeterek.flightlidar48.database;

import org.fpeterek.flightlidar48.records.Airport;
import org.fpeterek.flightlidar48.records.Flight;

import java.sql.PreparedStatement;
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

  public Flight withAirports(Flight flight) throws SQLException {

    final var query = baseQuery() + " WHERE icao=% or icao=%;";
    PreparedStatement stmt = conn.prepareStatement(query);
    stmt.setString(1, flight.originIcao());
    stmt.setString(2, flight.destinationIcao());

    var rs = stmt.executeQuery();

    while (rs.next()) {
      flight = flight.addAirport(extractOne(rs));
    }
    return flight;

  }

  public void setAirports(List<Flight> flights) throws SQLException {
    for (int i = 0; i < flights.size(); ++i) {
      flights.set(i, withAirports(flights.get(i)));
    }
  }
}
