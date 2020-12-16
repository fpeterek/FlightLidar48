package org.fpeterek.flightlidar48.validator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReceiverData {

  final int id;


  private static class DataTracker {

    private final long dataSizeThreshold = 1 * 1000 * 1000;
    private final long halfThreshold = dataSizeThreshold / 2;

    private static class Data {
      public long firstTs = 0;
      public long validReq = 0;
      public long totalReq = 0;
    }

    private Data current = new Data();
    private Data next = null;

    private long firstTs() {
      return current.firstTs;
    }

    private long validReq() {
      return current.validReq;
    }

    private long totalReq() {
      return current.totalReq;
    }

    private void updateDataHolders() {
      if (next == null && current.totalReq >= halfThreshold) {
        next = new Data();
      }
      // Theoretically the second condition should only ever be true if the first one is true as well
      // However, I choose to include it as a safety measure, in case there's some weird bug in the code
      if (current.totalReq >= dataSizeThreshold || ((next != null) && next.totalReq >= halfThreshold)) {
        current = next;
        next = new Data();
      }
    }

    private void updateTs() {
      if (firstTs() == 0) {
        long ts = System.currentTimeMillis();
        current.firstTs = ts;
        if (next != null) {
          next.firstTs = ts;
        }
      }
    }

    private void updateReqCount(boolean valid) {
      if (valid) {
        ++current.validReq;
      }
      ++current.totalReq;
      if (next != null) {
        if (valid) {
          ++next.validReq;
        }
        ++next.totalReq;
      }
    }

  }

  private final DataTracker tracker = new DataTracker();

  private boolean notifiable = true;

  private final Lock lock = new ReentrantLock();

  public ReceiverData(int receiverId) {
    id = receiverId;
  }

  public void addRequest(boolean valid) {
    try {
      lock.lock();
      addRequestLocked(valid);
    } finally {
      lock.unlock();
    }
  }

  private void addRequestLocked(boolean valid) {
    tracker.updateTs();
    tracker.updateReqCount(valid);
    updateNotifiable();
  }

  private void updateNotifiable() {
    if (calcValidPercentage() >= 70) {
      notifiable = true;
    }
  }

  public void setNotified() {
    try {
      lock.lock();
      notifiable = false;
    } finally {
      lock.unlock();
    }
  }

  public boolean isNotifiable() {
    try {
      lock.lock();
      return notifiable;
    } finally {
      lock.unlock();
    }
  }

  private long calcInvalidRequests() {
    return tracker.totalReq() - tracker.validReq();
  }

  public long invalidRequests() {
    try {
      lock.lock();
      return calcInvalidRequests();
    } finally {
      lock.unlock();
    }
  }

  public long validRequests() {
    try {
      lock.lock();
      return tracker.validReq();
    } finally {
      lock.unlock();
    }
  }

  public long totalRequests() {
    try {
      lock.lock();
      return tracker.totalReq();
    } finally {
      lock.unlock();
    }
  }

  private double calcValidRatio() {
    if (tracker.totalReq() <= 0) {
      return 0;
    }
    return (double)tracker.validReq() / (double)tracker.totalReq();
  }

  public double validRatio() {
    try {
      lock.lock();
      return calcValidRatio();
    } finally {
      lock.unlock();
    }
  }

  private int calcValidPercentage() {
    return (int) (calcValidRatio() * 100);
  }

  public int validPercentage() {
    try {
      lock.lock();
      return calcValidPercentage();
    } finally {
      lock.unlock();
    }
  }

  private double calcInvalidRatio() {
    return 1 - calcValidRatio();
  }

  public double invalidRatio() {
    try {
      lock.lock();
      return calcInvalidRatio();
    } finally {
      lock.unlock();
    }
  }

  private int calcInvalidPercentage() {
    return 100 - calcValidPercentage();
  }

  public int invalidPercentage() {
    try {
      lock.lock();
      return calcInvalidPercentage();
    } finally {
      lock.unlock();
    }
  }

  private long calcAvgReqTime() {
    if (tracker.totalReq() == 0) {
      return 1000;
    }
    return (System.currentTimeMillis() - tracker.firstTs()) / tracker.totalReq();
  }

  public long avgReqTime() {
    try {
      lock.lock();
      return calcAvgReqTime();
    } finally {
      lock.unlock();
    }
  }

}
