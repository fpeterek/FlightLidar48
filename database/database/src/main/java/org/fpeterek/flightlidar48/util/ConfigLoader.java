package org.fpeterek.flightlidar48.util;

import java.util.Arrays;
import java.util.List;

public class ConfigLoader {

  public static String getString(String key, String defaultVal) {
    final var value = System.getenv(key);

    if (value == null || value.isBlank()) {
      return defaultVal;
    }
    return value;
  }

  public static String getString(String key) {
    final var value = System.getenv(key);

    if (value == null || value.isBlank()) {
      throw new RuntimeException("Missing config for '" + key + "'");
    }
    return value;
  }

  public static int getInt(String key, int defaultVal) {
    final var value = System.getenv(key);

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
      return defaultVal;
    }

  }

  public static int getInt(String key) {

    final var value = System.getenv(key);

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
      throw new RuntimeException("Missing or invalid config for '" + key + "'");
    }
  }

  public static final List<String> booleans = Arrays.asList("true", "1", "false", "0");

  public static boolean getBool(String key, boolean defaultVal) {
    final var value = getString(key, String.valueOf(defaultVal)).toLowerCase();

    if (value.isBlank()) {
      return defaultVal;
    } else if (!booleans.contains(value)) {
      throw new RuntimeException("Invalid config for '" + key + "'");
    }
    return value.equals("true") || value.equals("1");
  }

  public static boolean getBool(String key) {
    final var value = getString(key).toLowerCase();

    if (!booleans.contains(value)) {
      throw new RuntimeException("Invalid config for '" + key + "'");
    }
    return value.equals("true") || value.equals("1");
  }

}
