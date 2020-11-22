package org.fpeterek.flightlidar48.validator;

public class Main {

  public static void main(
      String[] javaIsPain) {

    var validator = new Validator();
    var streams = new StreamsApp(validator);
    streams.run();

  }

}
