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

  // Initialize an empty List once so we don't have to recreate an empty list many times throughout the runtime of
  // the entire application
  private final List<String> empty = new ArrayList<>();

  public FlightLidar48() throws SQLException { }

  public List<Flight> getFlights(GeoPoint lt, GeoPoint rb) throws SQLException {
    var low = new GeoPoint(Math.min(lt.lat(), rb.lat()), Math.min(lt.lon(), rb.lon()));
    var high = new GeoPoint(Math.max(lt.lat(), rb.lat()), Math.max(lt.lon(), rb.lon()));

    return dataproxy.getFlights(low, high);
  }

  private boolean isRegistrationPrefix(String prefix) {
    if (prefix.length() > 6) {
      return false;
    }

    int dashes = 0;
    for (int i = 0; i < prefix.length(); ++i) {

      char c = prefix.charAt(i);

      if (c == '-') {
        ++dashes;
      }
      if (Character.isAlphabetic(c) || Character.isDigit(c) || (c == '-' && (i == 1 || i == 2))) {
        continue;
      }
      return false;
    }

    return dashes == 1 || prefix.length() <= 2;
  }

  private boolean isFlightPrefix(String prefix) {
    if (prefix.length() > 6) {
      return false;
    }

    for (int i = 0; i < Math.min(2, prefix.length()); ++i) {
      char c = prefix.charAt(i);
      if (!Character.isAlphabetic(c)) {
        return false;
      }
    }

    for (int i = 2; i < prefix.length(); ++i) {
      char c = prefix.charAt(i);
      if (!Character.isDigit(c)) {
        return false;
      }
    }

    return true;
  }

  private boolean isAirportPrefix(String prefix) {
    if (prefix.length() > 4) {
      return false;
    }

    return prefix.chars().allMatch(Character::isAlphabetic);
  }

  private boolean isRegistration(String term) {
    if (term.length() != 6) {
      return false;
    }

    int dashes = 0;

    for (int i = 0; i < term.length(); ++i) {
      char c = term.charAt(i);

      if (c == '-') {
        ++dashes;
      }

      if (c == '-' && i != 1 && i != 2) {
        return false;
      }
      if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '-') {
        continue;
      }
      return false;
    }

    return dashes == 1;
  }

  private boolean isFlight(String term) {
    if (term.length() < 3) {
      return false;
    }

    if (! (Character.isAlphabetic(term.charAt(0)) && Character.isAlphabetic(term.charAt(1))) ) {
      return false;
    }

    for (int i = 2; i < term.length(); ++i) {
      if (!Character.isDigit(term.charAt(i))) {
        return false;
      }
    }

    return true;
  }

  private boolean isAirport(String term) {
    if (term.length() != 4) {
      return false;
    }

    return term.chars().allMatch(Character::isAlphabetic);
  }

  private List<String> suggestAircraft(String prefix, int limit, int retries) {

    if (limit <= 0 || !isRegistrationPrefix(prefix)) {
      return empty;
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

    return empty;
  }

  private List<String> suggestFlights(String prefix, int limit, int retries) {

    if (limit <= 0 || !isFlightPrefix(prefix)) {
      return empty;
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

    return empty;
  }

  private List<String> suggestAirports(String prefix, int limit, int retries) {

    if (limit <= 0 || !isAirportPrefix(prefix)) {
      return empty;
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

    return empty;
  }

  public List<String> suggest(String prefix) {

    prefix = prefix.toUpperCase();

    var results = new ArrayList<String>();
    results.addAll(suggestFlights(prefix, 10, 1));
    results.addAll(suggestAircraft(prefix, 10 - results.size(), 1));
    results.addAll(suggestAirports(prefix, 10 - results.size(), 1));

    return results.stream().limit(10).collect(Collectors.toList());
  }

  private Flight searchFlight(String number, int retries) {

    try {
      return dataproxy.findFlight(number);
    } catch (Exception e) {
      System.out.println("Database call failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return searchFlight(number, retries-1);
      }
    }

    return null;
  }

  private Flight searchAirborneAircraft(String registration, int retries) {

    try {
      return dataproxy.findFlightByAircraft(registration);
    } catch (Exception e) {
      System.out.println("Database call failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return searchAirborneAircraft(registration, -1);
      }
    }

    return null;
  }

  private Airport searchAirport(String icao, int retries) {

    try {
      return dataproxy.findAirport(icao);
    } catch (Exception e) {
      System.out.println("Database call failed with exception " + e.getClass());
      e.printStackTrace();

      if (retries > 0) {
        return searchAirport(icao, -1);
      }
    }

    return null;
  }

  public SearchResult search(String term) {

    term = term.toUpperCase();

    Flight fl = null;
    if (isFlight(term)) {
      fl = searchFlight(term, 1);
    } else if (isRegistration(term)) {
      fl = searchAirborneAircraft(term, 1);
    }

    if (isAirport(term)) {
      return new SearchResult(null, null, searchAirport(term, 1));
    }

    if (fl == null) {
      return new SearchResult(null, null, null);
    }
    return new SearchResult(fl, fl.aircraft(), null);
  }

}
