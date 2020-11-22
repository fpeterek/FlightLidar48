package org.fpeterek.flightlidar48.validator;

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

  final String inputTopic;
  final String outputTopic;
  final String brokerList;
  final String consumerId;
  final String producerId;
  final String locality;
  final boolean writeToStdout;

  private Config(String input, String output, String brokers, String consId, String prodId, String loc, boolean stdout) {
    inputTopic = input;
    outputTopic = output;
    brokerList = brokers;
    consumerId = consId;
    producerId = prodId;
    locality = loc;
    writeToStdout = stdout;
  }

  private Config() {
    this(
      getString("INPUT_TOPIC", "receiver-input-topic-" + getString("LOCALITY", "dev")),
      getString("OUTPUT_TOPIC", "validated-input-topic-" + getString("LOCALITY", "dev")),
      getString("BROKER_LIST", "127.0.0.1:9092"),
      getString("PRODUCER_ID", "ReceiverInputValidator"),
      getString("CONSUMER_ID", "ReceiverInputValidator"),
      getString("LOCALITY", "dev"),
      getBool("WRITE_TO_STDOUT", false)
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
