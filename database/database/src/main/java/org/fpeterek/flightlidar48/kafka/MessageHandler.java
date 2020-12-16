package org.fpeterek.flightlidar48.kafka;

public interface MessageHandler {
  void handleMessage(String key, String value);
}

