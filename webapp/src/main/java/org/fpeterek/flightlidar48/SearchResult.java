package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Airport;
import org.fpeterek.flightlidar48.database.records.Aircraft;
import org.fpeterek.flightlidar48.database.records.Flight;

public record SearchResult(
  Flight flight,
  Aircraft aircraft,
  Airport airport
) { }
