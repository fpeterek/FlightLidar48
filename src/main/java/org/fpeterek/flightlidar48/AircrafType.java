package org.fpeterek.flightlidar48;

public record AircrafType(
        String designator,
        String manufacturer,
        String family,
        String subtype) {

    String type() {
        return (subtype == null || subtype.isBlank()) ? family : (family + "-" + subtype);
    }

    String fullType() {
        return manufacturer + " " + type();
    }

}
