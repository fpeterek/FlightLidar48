package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.util.MultiValueMap;

import java.net.URI;
import java.util.List;


class RESTApi {

  private final RatpackServer server;
  private final FlightLidar48 fl48 = new FlightLidar48();

  public RESTApi() throws Exception {
    server = RatpackServer.start(server ->
      server
        .serverConfig(builder ->
          builder.baseDir(BaseDir.find()).publicAddress(new URI("http://flightlidar48.org"))
        )
        .registryOf(registry ->
          registry.add("World!")
        )
        .handlers(chain ->
          chain
            .files(
              f -> f.dir("static").indexFiles("index.html")
            )
            .prefix("get", getChain -> getChain.get(
                handler -> {
                  handler.getResponse().getHeaders()
                    .set("Access-Control-Allow-Origin", "127.0.0.1, localhost")
                    .set("Access-Control-Allow-Methods", "GET, POST, PUT");
                  var data = getAircraft(handler.getRequest().getQueryParams());
                  handler.render(data);
                })
            )
        )
    );
  }


  private String getAircraft(GeoPoint lb, GeoPoint rt, int retries) {

    List<Flight> aircraft;
    try {
      Metrics.dbCalls.inc();
      aircraft = fl48.getFlights(lb, rt);
    } catch (Exception e) {

      Metrics.failedCalls.inc();
      System.out.println("Failed to fetch flights with exception " + e.getClass());
      System.out.println(e.getMessage());
      e.printStackTrace();

      if (retries > 0) {
        return getAircraft(lb, rt, retries-1);
      }
      System.out.println("Failed to fetch flight even after retry.");
      return JsonFormatter.error("Could not fetch aircraft from database.");
    }

    return JsonFormatter.mapRecords(aircraft);
  }

  private String getAircraft(MultiValueMap<String, String> params) {

    final var lb = new GeoPoint(Double.parseDouble(params.get("lby")), Double.parseDouble(params.get("lbx")));
    final var rt = new GeoPoint(Double.parseDouble(params.get("rty")), Double.parseDouble(params.get("rtx")));
    return getAircraft(lb, rt, 1);

  }

}
