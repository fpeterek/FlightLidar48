package org.fpeterek.flightlidar48;


public class Main {

  public static void main(String[] args) {
    try {
      RESTApi api = new RESTApi();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
