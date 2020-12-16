package org.fpeterek.flightlidar48.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import java.util.Properties;


public class MessageWriter {

  private final String topic;

  private static Properties initProps(String brokers, String producerID) {
    var props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, producerID);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return props;
  }

  private final KafkaProducer<String, String> producer;

  private final ProducerRecord<String, String> healthcheckRecord = createRecord(
    new JSONObject().put("healthcheck", "healthcheck").toString()
  );

  public MessageWriter(String brokers, String producerID, String outTopic) {
    if (brokers == null || producerID == null || outTopic == null) {
      throw new IllegalArgumentException("Value might not be null (use Kotlin)");
    }
    topic = outTopic;
    producer = new KafkaProducer<>(initProps(brokers, producerID));
  }

  private ProducerRecord<String, String> createRecord(String data) {
    return new ProducerRecord<>(topic, data);
  }

  public void writeHealthcheck() {
    producer.send(healthcheckRecord);
  }

  public void write(String data) {
    producer.send(createRecord(data));
  }

}
