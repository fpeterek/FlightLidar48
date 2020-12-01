package org.fpeterek.flightlidar48.writer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class Healthcheck {

  private Thread thread;
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  private final long intervalMS = 60 * 1_000; // 60 seconds

  private final String topic = Config.get().inputTopic;

  private static Properties initProps() {
    var props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.get().brokerList);
    props.put(ProducerConfig.CLIENT_ID_CONFIG, Config.get().consumerId + "Healthcheck");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return props;
  }

  private final KafkaProducer<String, String> producer = new KafkaProducer<>(initProps());

  private final ProducerRecord<String, String> record = new ProducerRecord<>(
    topic, new JSONObject().put("healthcheck", "healthcheck").toString()
  );

  public void healthcheck() {
    producer.send(record);
  }

  public void loop() {
    while (isRunning.get()) {
      try {
        healthcheck();
        Thread.sleep(intervalMS);
      } catch (InterruptedException ignored) {
        return;
      }
    }
  }

  public void run() {
    isRunning.set(true);
    thread = new Thread(this::loop);
    thread.start();
  }

  public void stop() {
    try {
      isRunning.set(false);
      thread.join();
    } catch (InterruptedException ignored) {

    }
  }

}
