package org.fpeterek.flightlidar48.validator;


import org.fpeterek.flightlidar48.util.ConfigLoader;

public class Config {

  public final String inputTopic;
  public final String outputTopic;
  public final String brokerList;
  public final String consumerId;
  public final String producerId;
  public final String locality;
  public final boolean writeToStdout;
  public final int ddosThreshold;
  public final int assessmentThreshold;
  public final int threads;
  public final String emailUser;
  public final String emailPassword;

  private Config(String input, String output, String brokers, String consId, String prodId, String loc, boolean stdout,
                 int ddos, int assessment, int streamsThreads, String user, String passwd) {
    inputTopic = input;
    outputTopic = output;
    brokerList = brokers;
    consumerId = consId;
    producerId = prodId;
    locality = loc;
    writeToStdout = stdout;
    ddosThreshold = ddos;
    assessmentThreshold = assessment;
    threads = streamsThreads;
    emailUser = user;
    emailPassword = passwd;
  }

  private Config() {
    this(
      ConfigLoader.getString("INPUT_TOPIC", "receiver-input-topic") +
        "-" + ConfigLoader.getString("LOCALITY", "dev"),
      ConfigLoader.getString("OUTPUT_TOPIC", "validated-input-topic") +
        "-" + ConfigLoader.getString("LOCALITY", "dev"),
      ConfigLoader.getString("BROKER_LIST", "127.0.0.1:9092"),
      ConfigLoader.getString("PRODUCER_ID", "ReceiverInputValidator"),
      ConfigLoader.getString("CONSUMER_ID", "ReceiverInputValidator"),
      ConfigLoader.getString("LOCALITY", "dev"),
      ConfigLoader.getBool("WRITE_TO_STDOUT", false),
      ConfigLoader.getInt("DDOS_THRESHOLD", 5),
      ConfigLoader.getInt("ASSESSMENT_THRESHOLD", 1000),
      ConfigLoader.getInt("THREADS", 3),
      ConfigLoader.getString("EMAIL_USER"),
      ConfigLoader.getString("EMAIL_PASSWORD")
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
