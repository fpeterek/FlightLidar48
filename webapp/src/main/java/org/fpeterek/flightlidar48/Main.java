package org.fpeterek.flightlidar48;

import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

import java.net.URI;

import org.fpeterek.flightlidar48.database.gateways.AirportGateway;
import org.fpeterek.flightlidar48.database.gateways.CountryGateway;


public class Main {

  public static void run() throws Exception {
    RatpackServer.start(server ->
      server
        .serverConfig(builder ->
          builder.baseDir(BaseDir.find()).publicAddress(new URI("http://flightlidar48.org"))
        )
        .registryOf(registry ->
          registry.add("World!")
        )
        .handlers(chain ->
          chain
            .files(f -> f.dir("static").indexFiles("index.html"))
        )
    );
  }

  public static void main(String[] args) {
    try {
      // run();
      var gw = new AirportGateway();
      var cgw = new CountryGateway();
      var airports = gw.get();
      cgw.setCountries(airports);
      airports.forEach(System.out::println);

      run();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
