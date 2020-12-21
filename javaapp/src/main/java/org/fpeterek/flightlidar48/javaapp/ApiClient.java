package org.fpeterek.flightlidar48.javaapp;

import org.fpeterek.flightlidar48.util.GeoPoint;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ApiClient {
  private final String url = "127.0.0.1";
  private final String endpoint = "get";
  private final int port = 5050;
  private final String scheme = "http";

  private String formatUrl(GeoPoint lb, GeoPoint rt) {
    return scheme + "://" + url + ":" + port + "/" + endpoint + "/" +
      "?lby=" + lb.lat() + "&lbx=" + lb.lon() + "&rty=" + rt.lat() + "&rtx=" + rt.lon();
  }

  private URL getUrl(GeoPoint lb, GeoPoint rt) throws MalformedURLException {
    return new URL(formatUrl(lb, rt));
  }

  private HttpURLConnection createConn(URL url) throws IOException {
    var conn = (HttpURLConnection)url.openConnection();
    conn.setRequestMethod("GET");
    return conn;
  }

  private String loadResponse(HttpURLConnection conn) throws IOException {
    var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inline;
    var sb = new StringBuilder();

    while ((inline = reader.readLine()) != null ) {
      sb.append(inline);
    }

    reader.close();

    return sb.toString();
  }

  private List<Aircraft> parseResponse(String resp) throws Exception {

    var json = new JSONObject(resp);

    if (
        !json.has("status") ||
        (json.has("status") && !json.getString("status").equals("success")) ||
        !json.has("results")
      ) {
      throw new Exception("Invalid response from backend");
    }

    var arr = json.getJSONArray("results");

    var out = new ArrayList<Aircraft>();

    for (int i = 0; i < arr.length(); ++i) {
      var ac = arr.getJSONObject(i);

      out.add(
        new Aircraft(
          ac.getDouble("lat"),
          ac.getDouble("lon"),
          ac.getDouble("direction"),
          ac.getInt("speed"),
          ac.getString("aircraft")
        )
      );
    }

    return out;
  }

  public List<Aircraft> get(GeoPoint lb, GeoPoint rt) throws Exception {

    var conn = createConn(getUrl(lb, rt));
    var code = conn.getResponseCode();

    if (code != HttpURLConnection.HTTP_OK) {
      throw new IOException("Call failed");
    }

    return parseResponse(loadResponse(conn));
  }
}
