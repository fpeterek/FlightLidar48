package org.fpeterek.flightlidar48.writer;


import org.fpeterek.flightlidar48.util.ConfigLoader;

public class Config {

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
      ConfigLoader.getString("INPUT_TOPIC", "validated-input-topic")
        + "-" + ConfigLoader.getString("LOCALITY", "dev"),
      ConfigLoader.getString("BROKER_LIST", "127.0.0.1:9092"),
      ConfigLoader.getString("CONSUMER_ID", "FLDatabaseWriter"),
      ConfigLoader.getString("LOCALITY", "dev"),
      ConfigLoader.getInt("THREADS", 3),
      ConfigLoader.getString("DB_URL", "jdbc:postgresql://localhost:5432/flightlidar"),
      ConfigLoader.getString("DB_USER", "fpeterek"),
      ConfigLoader.getString("DB_PASSWORD", ""),
      ConfigLoader.getInt("DELETION_THRESHOLD_MS", 10000),
      ConfigLoader.getInt("HEALTHCHECK_PERIOD_MS", 10000)
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