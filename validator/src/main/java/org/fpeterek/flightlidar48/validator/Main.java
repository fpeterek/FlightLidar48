package org.fpeterek.flightlidar48.validator;

import org.fpeterek.flightlidar48.kafka.MessageHandler;
import org.fpeterek.flightlidar48.kafka.MessageStream;

public class Main {

  private static MessageStream createStream(MessageHandler handler) {

    return MessageStream.builder()
      .setBrokerList(Config.get().brokerList)
      .setConsumerID(Config.get().consumerId)
      .setInputTopic(Config.get().inputTopic)
      .setThreadCount(Config.get().threads)
      .setHandler(handler)
      .build();

  }

  public static void main(
      String[] javaIsPain) {

    var validator = new Validator();
    var streams = createStream(validator);
    streams.run();

  }

}
