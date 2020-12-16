package org.fpeterek.flightlidar48.writer;

import org.fpeterek.flightlidar48.kafka.MessageWriter;

import java.util.concurrent.atomic.AtomicBoolean;


public class Healthcheck {

  private Thread thread;
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  private final long intervalMS = Config.get().healthcheckThreshold;
  private final MessageWriter writer = new MessageWriter(
    Config.get().brokerList, Config.get().consumerId + "Healthcheck", Config.get().inputTopic
  );

  public void healthcheck() {
    writer.writeHealthcheck();
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
