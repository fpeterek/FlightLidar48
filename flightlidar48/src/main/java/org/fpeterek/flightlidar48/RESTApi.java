package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.database.records.Flight;
import org.fpeterek.flightlidar48.util.GeoPoint;
import org.json.JSONArray;
import org.json.JSONObject;
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
            .prefix("suggest", getChain -> getChain.get(
              handler -> {
                handler.getResponse().getHeaders()
                  .set("Access-Control-Allow-Origin", "127.0.0.1, localhost")
                  .set("Access-Control-Allow-Methods", "GET, POST, PUT");
                var data = suggest(handler.getRequest().getQueryParams());
                handler.render(data);
              })
            )
            .prefix("search", getChain -> getChain.get(
            handler -> {
              handler.getResponse().getHeaders()
                .set("Access-Control-Allow-Origin", "127.0.0.1, localhost")
                .set("Access-Control-Allow-Methods", "GET, POST, PUT");
              var data = search(handler.getRequest().getQueryParams());
              handler.render(data);
            })
          )
        )
    );
  }

  private String search(MultiValueMap<String, String> params) {
    return JsonFormatter.searchResult(
      fl48.search(params.getOrDefault("term", ""))
    );
  }

  private String suggest(MultiValueMap<String, String> params) {

    var array = new JSONArray();
    var suggestions = fl48.suggest(params.getOrDefault("term", ""));

    suggestions.forEach(array::put);

    return array.toString();
  }

  private String getAircraft(GeoPoint lb, GeoPoint rt, String tracked, int retries) {

    List<Flight> aircraft;
    SearchResult sr = null;

    try {
      Metrics.dbCalls.inc();
      aircraft = fl48.getFlights(lb, rt);
      if (tracked != null && !tracked.isBlank()) {
        sr = fl48.search(tracked);
      }
    } catch (Exception e) {

      Metrics.failedCalls.inc();
      System.out.println("Failed to fetch flights with exception " + e.getClass());
      System.out.println(e.getMessage());
      e.printStackTrace();

      if (retries > 0) {
        return getAircraft(lb, rt, tracked, retries-1);
      }
      System.out.println("Failed to fetch flight even after retry.");
      return JsonFormatter.error("Could not fetch aircraft from database.");
    }

    return JsonFormatter.mapRecords(aircraft, sr);
  }

  private String getAircraft(MultiValueMap<String, String> params) {

    final var lb = new GeoPoint(Double.parseDouble(params.get("lby")), Double.parseDouble(params.get("lbx")));
    final var rt = new GeoPoint(Double.parseDouble(params.get("rty")), Double.parseDouble(params.get("rtx")));
    final var tracked = params.getOrDefault("tracked", "");
    return getAircraft(lb, rt, tracked, 1);

  }

}
