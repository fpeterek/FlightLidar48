package org.fpeterek.flightlidar48;


import io.prometheus.client.exporter.HTTPServer;

public class Main {

  public static void main(String[] args) {
    try {
      RESTApi api = new RESTApi();
      HTTPServer metrics = new HTTPServer(Config.get().metricsPort);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
