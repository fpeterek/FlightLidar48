package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.kafka.MessageHandler;
import org.fpeterek.flightlidar48.kafka.MessageStream;

import java.sql.SQLException;

public class Main {

  private static void runStreams(MessageStream streams, Healthcheck healthcheck) {
    try {

      healthcheck.run();
      streams.run();

    } catch (Exception e) {

      e.printStackTrace();
      healthcheck.stop();
      System.exit(-1);

    }
  }

  private static MessageStream createStream(MessageHandler handler) {

    return MessageStream.builder()
      .setBrokerList(Config.get().brokerList)
      .setConsumerID(Config.get().consumerId)
      .setInputTopic(Config.get().inputTopic)
      .setThreadCount(Config.get().threads)
      .setHandler(handler)
      .build();

  }

  public static void run() {
    try {

      var writer = new Writer();
      var streams = createStream(writer);
      var healthcheck = new Healthcheck();
      runStreams(streams, healthcheck);

    } catch (SQLException ex) {

      ex.printStackTrace();
      System.exit(-1);

    }
  }

  public static void main(
      String[] javaIsPain) {
    run();
  }

}
