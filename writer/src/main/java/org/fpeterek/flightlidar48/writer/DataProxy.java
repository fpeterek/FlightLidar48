package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.database.gateways.CurrentFlightGateway;
import org.fpeterek.flightlidar48.database.gateways.FlightGateway;
import org.fpeterek.flightlidar48.database.records.CurrentFlight;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.writer.data.FlightData;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public Map<String, FlightData> fetchFlights() throws SQLException {

    var fl = flights.fetchAirborne();
    var map = new HashMap<String, FlightData>();

    fl.forEach(it -> map.put(it.number(), new FlightData(it, null)));

    return map;
  }

  public Flight fetchLatest(String flight) throws SQLException {
    return flights.fetchLatest(flight);
  }

  public Flight createAndGetFlight(String number, String orig, String dest, String aircraft) throws SQLException {
    flights.createFlight(number, orig, dest, aircraft);
    return fetchLatest(number);
  }

  public void createCurrent(long id, double lat, double lon, int squawk, int altitude, double direction, int speed) throws SQLException {
    current.create(id, lat, lon, squawk, altitude, direction, speed);
  }

  public Flight withCurrent(Flight fl) throws SQLException {
    return current.withFlight(fl);
  }

  public void updateCurrent(CurrentFlight cf) throws SQLException {
    current.update(cf);
  }

  public void landFlights(List<Flight> toLand) throws SQLException {
    flights.landFlights(toLand);
    current.removeLanded();
  }

}
