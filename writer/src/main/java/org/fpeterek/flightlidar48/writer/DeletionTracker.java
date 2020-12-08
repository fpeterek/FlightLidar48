package org.fpeterek.flightlidar48.writer;

public class DeletionTracker {

  private final int threshold = Config.get().deletionThresholdMs;
  private long last = now();

  private long now() {
    return System.currentTimeMillis();
  }

  private long deletionTime() {
    return last + threshold;
  }

  private boolean shouldDelete() {
    return deletionTime() <= now();
  }

  public boolean status() {
    if (shouldDelete()) {
      last = now();
      return true;
    }
    return false;
  }

}
