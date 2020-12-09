package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.gateways.CurrentFlightGateway;
import org.fpeterek.flightlidar48.database.gateways.FlightGateway;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;

import java.sql.SQLException;
import java.util.List;

public class DataProxy {

  private final FlightGateway flights;
  private final CurrentFlightGateway current;

  public DataProxy(FlightGateway flgw, CurrentFlightGateway cfgw) {
    flights = flgw;
    current = cfgw;
  }

  public DataProxy() throws SQLException {
    flights = new FlightGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
    current = new CurrentFlightGateway(Config.get().dbUrl, Config.get().dbUser, Config.get().dbPass);
  }

  public List<Flight> getFlights(GeoPoint low, GeoPoint high) throws SQLException {
    var fl = flights.fetchFlights(low, high);
    current.setFlights(fl);
    return fl;
  }

}
