package org.fpeterek.flightlidar48.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;


public class MessageStream {

  private final KafkaStreams streams;

  private static Properties createProperties(String consumerId, String brokerList, int threads) {
    var props = new Properties();

    props.put(StreamsConfig.APPLICATION_ID_CONFIG, consumerId);
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
    props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, threads);
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    return props;
  }

  private MessageStream(String consumerId, String brokerList, String inputTopic, int threads, MessageHandler handler) {

    var builder = new StreamsBuilder();
    KStream<String, String> stream = builder.stream(inputTopic);
    stream.foreach(handler::handleMessage);

    streams = new KafkaStreams(builder.build(), createProperties(consumerId, brokerList, threads));
  }

  public void run() {
    streams.start();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private String consId;
    private String brokerList;
    private int threads = 0;
    private String inputTopic;
    private MessageHandler handler;


    private void throwNullError(String field) {
      throw new IllegalArgumentException(field + " must not be null.");
    }

    private void throwIfNull(String field, Object value) {
      if (value == null) {
        throwNullError(field);
      }
    }

    private void checkAll() {
      if (threads <= 0) {
        throw new IllegalArgumentException("Number of threads must be greater than zero (supplied value: " + threads + ")");
      }

      throwIfNull("Consumer ID", consId);
      throwIfNull("Broker List", brokerList);
      throwIfNull("Input Topic", inputTopic);
      throwIfNull("Message Handler", handler);
    }

    public Builder setConsumerID(String consumerID) {
      throwIfNull("Consumer ID", consumerID);
      consId = consumerID;
      return this;
    }

    public Builder setBrokerList(String list) {
      throwIfNull("Broker List", list);
      brokerList = list;
      return this;
    }

    public Builder setThreadCount(int threadCount) {
      if (threadCount <= 0) {
        throw new IllegalArgumentException("Number of threads must be greater than zero (supplied value: " + threadCount + ")");
      }
      threads = threadCount;
      return this;
    }

    public Builder setInputTopic(String inTopic) {
      throwIfNull("Input Topic", inTopic);
      inputTopic = inTopic;
      return this;
    }

    public Builder setHandler(MessageHandler messageHandler) {
      throwIfNull("Message Handler", messageHandler);
      handler = messageHandler;
      return this;
    }

    public MessageStream build() {
      checkAll();
      return new MessageStream(consId, brokerList, inputTopic, threads, handler);
    }

  }

}
