package org.fpeterek.flightlidar48.writer.data;

import org.apache.kafka.common.protocol.types.Field;
import org.fpeterek.flightlidar48.database.records.Flight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookKeeper {

  private final Map<String, FlightData> data;
  private final Map<String, Long> acquired = new HashMap<>();

  private final Lock lock = new ReentrantLock();

  public BookKeeper(Map<String, FlightData> initialData) {
    data = initialData;
  }

  public void put(String number, FlightData flightData) {
    try {
      lock.lock();
      data.put(number, flightData);
    } finally {
      lock.unlock();
    }
  }

  public BookKeeperRecord acquire(String number) {
    try {
      lock.lock();
      if (acquired.containsKey(number)) {
        return BookKeeperRecord.failure;
      }
      acquired.put(number, System.currentTimeMillis());
      return BookKeeperRecord.success(data.getOrDefault(number, null));
    } finally {
      lock.unlock();
    }
  }

  public void release(String number) {
    try {
      lock.lock();
      acquired.remove(number);
    } finally {
      lock.unlock();
    }
  }

  private List<Flight> removeOldFlightData(long threshold) {
   var toRemove = data
      .values()
      .stream()
      .filter(it ->
        it.timestamp() <= threshold
      )
      .map(FlightData::flight)
      .collect(Collectors.toList());

   toRemove.forEach(it -> data.remove(it.number()));

   return toRemove;
  }

  private void removeOldLocks(long threshold) {
    acquired
      .entrySet()
      .stream()
      .filter(entry ->
        entry.getValue() <= threshold
      )
      .map(Map.Entry::getKey)
      .collect(Collectors.toList())
      .forEach(acquired::remove);
  }

  public List<Flight> removeOld(long threshold) {
    try {
      lock.lock();
      removeOldLocks(threshold);
      return removeOldFlightData(threshold);
    } finally {
      lock.unlock();
    }
  }

}
