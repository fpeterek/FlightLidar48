package org.fpeterek.flightlidar48;

import java.util.List;

public record Airline(
    String designator,
    String name,
    List<Aircraft> fleet
) {
}
