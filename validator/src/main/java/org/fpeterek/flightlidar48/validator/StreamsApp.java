package org.fpeterek.flightlidar48.validator;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class StreamsApp {

  private final KafkaStreams streams;

  static Properties properties() {
    var props = new Properties();

    props.put(StreamsConfig.APPLICATION_ID_CONFIG, Config.get().consumerId);
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, Config.get().brokerList);
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    return props;
  }

  public StreamsApp(Validator validator) {

    var builder = new StreamsBuilder();
    KStream<String, String> stream = builder.stream(Config.get().inputTopic);
    stream.foreach(validator::validate);

    streams = new KafkaStreams(builder.build(), properties());

  }

  public void run() {
    streams.start();
  }

}
