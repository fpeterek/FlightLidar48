package org.fpeterek.flightlidar48;

import java.util.Arrays;
import java.util.List;


public class Config {

  private static String getString(String key, String defaultVal) {
    final var value = System.getenv(key);

    if (value == null || value.isBlank()) {
      return defaultVal;
    }
    return value;
  }

  private static String getString(String key) {
    final var value = System.getenv(key);

    if (value == null || value.isBlank()) {
      throw new RuntimeException("Missing config for '" + key + "'");
    }
    return value;
  }

  private static int getInt(String key, int defaultVal) {
    final var value = System.getenv(key);

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
      return defaultVal;
    }

  }

  private static int getInt(String key) {

    final var value = System.getenv(key);

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException ignored) {
      throw new RuntimeException("Missing or invalid config for '" + key + "'");
    }
  }

  private static final List<String> booleans = Arrays.asList("true", "1", "false", "0");

  private static boolean getBool(String key, boolean defaultVal) {
    final var value = getString(key, String.valueOf(defaultVal)).toLowerCase();

    if (value.isBlank()) {
      return defaultVal;
    } else if (!booleans.contains(value)) {
      throw new RuntimeException("Invalid config for '" + key + "'");
    }
    return value.equals("true") || value.equals("1");
  }

  private static boolean getBool(String key) {
    final var value = getString(key).toLowerCase();

    if (!booleans.contains(value)) {
      throw new RuntimeException("Invalid config for '" + key + "'");
    }
    return value.equals("true") || value.equals("1");
  }

  public final String dbUrl;
  public final String dbUser;
  public final String dbPass;

  private Config(String db, String user, String passwd) {
    dbUrl = db;
    dbUser = user;
    dbPass = passwd;
  }

  private Config() {
    this(
      getString("DB_URL", "jdbc:postgresql://localhost:5432/flightlidar"),
      getString("DB_USER", "fpeterek"),
      getString("DB_PASSWORD", "")
    );
  }

  private static Config instance = null;

  public static Config get() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

}
