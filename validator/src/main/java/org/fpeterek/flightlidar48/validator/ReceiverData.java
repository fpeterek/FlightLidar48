package org.fpeterek.flightlidar48.validator;

public class ReceiverData {

  final int id;

  private long firstTs = 0;
  private long validReq = 0;
  private long totalReq = 0;

  public ReceiverData(int receiverId) {
    id = receiverId;
  }

  public void addRequest(boolean valid) {
    updateTs();
    updateReqCount(valid);
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

  public long invalidRequests() {
    return totalReq - validReq;
  }

  public long validRequests() {
    return validReq;
  }

  public long totalRequests() {
    return totalReq;
  }

  public double validRatio() {
    return (double)validReq / (double)totalReq;
  }

  public int validPercentage() {
    return (int)(validRatio() * 100);
  }

  public double invalidRatio() {
    return 1 - validRatio();
  }

  public int invalidPercentage() {
    return 100 - validPercentage();
  }

  public long avgReqTime() {
    return (System.currentTimeMillis() - firstTs) / totalReq;
  }

}
