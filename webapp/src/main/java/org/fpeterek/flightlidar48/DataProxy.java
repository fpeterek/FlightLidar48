package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.gateways.AircraftGateway;
import org.fpeterek.flightlidar48.database.gateways.AirportGateway;
import org.fpeterek.flightlidar48.database.gateways.CurrentFlightGateway;
import org.fpeterek.flightlidar48.database.gateways.FlightGateway;
import org.fpeterek.flightlidar48.database.records.Aircraft;
import org.fpeterek.flightlidar48.database.records.Airport;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;

import java.sql.SQLException;
import java.util.List;

public class DataProxy {

  private final FlightGateway flights;
  private final CurrentFlightGateway current;
  private final AircraftGateway aircraft;
  private final AirportGateway airports;

  public DataProxy(FlightGateway flgw, CurrentFlightGateway cfgw, AircraftGateway acgw, AirportGateway apgw) {
    flights = flgw;
    current = cfgw;
    aircraft = acgw;
    airports = apgw;
  }

  public DataProxy() throws SQLException {
    flights = new FlightGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
    current = new CurrentFlightGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
    aircraft = new AircraftGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
    airports = new AirportGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
  }

  public List<Flight> getFlights(GeoPoint low, GeoPoint high) throws SQLException {
    var fl = flights.fetchFlights(low, high);
    current.setFlights(fl);
    return fl;
  }

  public List<Aircraft> suggestAircraft(String prefix, int limit) throws SQLException {
    return aircraft.searchByPrefix(prefix, limit);
  }

  public List<Flight> suggestFlights(String prefix, int limit) throws SQLException {
    return flights.searchByPrefix(prefix, limit);
  }

  public List <Airport> searchAirports(String prefix, int limit) throws SQLException {
    return airports.searchByPrefix(prefix, limit);
  }

}
