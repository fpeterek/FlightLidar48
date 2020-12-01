package org.fpeterek.flightlidar48.writer;

import java.sql.SQLException;

public class Main {

  private static void runStreams(StreamsApp streams, Healthcheck healthcheck) {
    try {

      healthcheck.run();
      streams.run();

    } catch (Exception e) {

      e.printStackTrace();
      healthcheck.stop();
      System.exit(-1);

    }
  }

  public static void run() {
    try {

      var writer = new Writer();
      var streams = new StreamsApp(writer);
      var healthcheck = new Healthcheck();
      runStreams(streams, healthcheck);

    } catch (SQLException ex) {

      ex.printStackTrace();
      System.exit(-1);

    }
  }

  public static void main(
      String[] javaIsPain) {
    run();
  }

}
