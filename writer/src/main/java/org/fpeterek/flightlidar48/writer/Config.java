package org.fpeterek.flightlidar48.writer;

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

  public final String inputTopic;
  public final String brokerList;
  public final String consumerId;
  public final String locality;

  public final int threads;
  public final String dbUrl;
  public final String dbUser;
  public final String dbPass;

  public final int deletionThresholdMs;
  public final int healthcheckThreshold;

  private Config(String input, String brokers, String consId, String loc, int streamsThreads, String db,
                 String user, String passwd, int deletionThreshold, int hcThreshold) {
    inputTopic = input;
    brokerList = brokers;
    consumerId = consId;
    locality = loc;
    threads = streamsThreads;
    dbUrl = db;
    dbUser = user;
    dbPass = passwd;
    deletionThresholdMs = deletionThreshold;
    healthcheckThreshold = hcThreshold;
  }

  private Config() {
    this(
      getString("INPUT_TOPIC", "validated-input-topic") + "-" + getString("LOCALITY", "dev"),
      getString("BROKER_LIST", "127.0.0.1:9092"),
      getString("CONSUMER_ID", "FLDatabaseWriter"),
      getString("LOCALITY", "dev"),
      getInt("THREADS", 3),
      getString("DB_URL", "jdbc:postgresql://localhost:5432/flightlidar"),
      getString("DB_USER", "fpeterek"),
      getString("DB_PASSWORD", ""),
      getInt("DELETION_THRESHOLD_MS", 10000),
      getInt("HEALTHCHECK_PERIOD_MS", 10000)
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