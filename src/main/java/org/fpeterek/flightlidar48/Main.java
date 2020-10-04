package org.fpeterek.flightlidar48;

import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.net.URI;

public class Main {

  public static void run() throws Exception {
    RatpackServer.start(server ->
        server
            .serverConfig(
                ServerConfig.embedded().publicAddress(new URI("http://flightlidar48.org"))
            )
            .registryOf(registry ->
                registry.add("World!")
            )
            .handlers(chain ->
              chain
                  .get(ctx -> ctx.render("Hello " + ctx.get(String.class)))
                  .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))
            )
    );
  }

  public static void main(String[] args) {
    try {
      run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
