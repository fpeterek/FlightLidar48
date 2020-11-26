package org.fpeterek.flightlidar48.validator;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaWriter {

  private final String topic = Config.get().outputTopic;

  private static Properties initProps() {
    var props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.get().brokerList);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, Config.get().producerId);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return props;
  }

  private final KafkaProducer<String, String> producer = new KafkaProducer<>(initProps());

  private ProducerRecord<String, String> createRecord(String data) {
    return new ProducerRecord<>(topic, data);
  }

  public void write(String data) {
    producer.send(createRecord(data));
  }
}
