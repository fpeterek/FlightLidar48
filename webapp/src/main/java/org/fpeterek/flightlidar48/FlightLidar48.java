package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Aircraft;
import org.fpeterek.flightlidar48.database.records.Airport;
import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightLidar48 {

  DataProxy dataproxy = new DataProxy();

  public FlightLidar48() throws SQLException { }

  public List<Flight> getFlights(GeoPoint lt, GeoPoint rb) throws SQLException {
    var low = new GeoPoint(Math.min(lt.lat(), rb.lat()), Math.min(lt.lon(), rb.lon()));
    var high = new GeoPoint(Math.max(lt.lat(), rb.lat()), Math.max(lt.lon(), rb.lon()));

    return dataproxy.getFlights(low, high);
  }

  private List<String> suggestAircraft(String prefix, int limit, int retries) {

    if (limit <= 0) {
      return new ArrayList<>();
    }

    try {
      return dataproxy.suggestAircraft(prefix, limit).stream().map(Aircraft::registration).collect(Collectors.toList());
    } catch (Exception e) {

      System.out.println("Call to PostgreSQL failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return suggestAircraft(prefix, limit, retries-1);
      }

    }

    return new ArrayList<>();
  }

  private List<String> suggestFlights(String prefix, int limit, int retries) {

    if (limit <= 0) {
      return new ArrayList<>();
    }

    try {
      return dataproxy.suggestFlights(prefix, limit).stream().map(Flight::number).collect(Collectors.toList());
    } catch (Exception e) {

      System.out.println("Call to PostgreSQL failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return suggestFlights(prefix, limit, retries-1);
      }

    }

    return new ArrayList<>();
  }

  private List<String> suggestAirports(String prefix, int limit, int retries) {

    if (limit <= 0) {
      return new ArrayList<>();
    }

    try {
      return dataproxy.searchAirports(prefix, limit).stream().map(Airport::icao).collect(Collectors.toList());
    } catch (Exception e) {

      System.out.println("Call to PostgreSQL failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return suggestAirports(prefix, limit, retries-1);
      }

    }

    return new ArrayList<>();
  }

  public List<String> suggest(String prefix) {

    prefix = prefix.toUpperCase();

    var results = suggestFlights(prefix, 10, 1);
    results.addAll(suggestAircraft(prefix, 10 - results.size(), 1));
    results.addAll(suggestAirports(prefix, 10 - results.size(), 1));

    return results;

  }

}
