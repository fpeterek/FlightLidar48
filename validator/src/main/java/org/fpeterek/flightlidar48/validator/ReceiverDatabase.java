package org.fpeterek.flightlidar48.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReceiverDatabase {

  private final Map<Integer, ReceiverData> receivers = new HashMap<>();
  private final Lock lock = new ReentrantLock();

  private ReceiverData getOrCreate(int id) {

    if (!receivers.containsKey(id)) {
      var recv = new ReceiverData(id);
      receivers.put(id, recv);
      return recv;
    }

    return receivers.get(id);
  }

  public ReceiverData get(int id) {
    try {
      lock.lock();
      return getOrCreate(id);
    } finally {
      lock.unlock();
    }
  }

}
