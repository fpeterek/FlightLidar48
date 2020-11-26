package org.fpeterek.flightlidar48.database.records;

import java.util.List;

public record Airline(
    String designator,
    String name,
    List<Aircraft> fleet
) {
  public void addToFleet(Aircraft ac) {
    if (!ac.airlineDesignator().equals(designator)) {
      throw new RuntimeException("Aircraft " + ac.registration() + " doesn't belong to " + name);
    }
    fleet.add(ac);
  }
}
