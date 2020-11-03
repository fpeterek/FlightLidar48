package org.fpeterek.flightlidar48;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {

  /*public static void run() throws Exception {
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
  }*/

  public static void main(String[] args) {
    try {
      // run();
      SpringApplication.run(Main.class, args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
