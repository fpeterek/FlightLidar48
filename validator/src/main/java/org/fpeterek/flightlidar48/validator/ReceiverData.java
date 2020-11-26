package org.fpeterek.flightlidar48.validator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReceiverData {

  final int id;

  private long firstTs = 0;
  private long validReq = 0;
  private long totalReq = 0;
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
    updateTs();
    updateReqCount(valid);
    updateNotifiable();
  }

  private void updateTs() {
    if (firstTs != 0) {
      firstTs = System.currentTimeMillis();
    }
  }

  private void updateReqCount(boolean valid) {
    if (valid) {
      ++validReq;
    }
    ++totalReq;
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
    return totalReq - validReq;
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
      return validReq;
    } finally {
      lock.unlock();
    }
  }

  public long totalRequests() {
    try {
      lock.lock();
      return totalReq;
    } finally {
      lock.unlock();
    }
  }

  private double calcValidRatio() {
    if (totalReq <= 0) {
      return 0;
    }
    return (double) validReq / (double) totalReq;
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
    if (totalReq == 0) {
      return 1000;
    }
    return (System.currentTimeMillis() - firstTs) / totalReq;
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
