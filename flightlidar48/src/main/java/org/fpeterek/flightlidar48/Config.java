package org.fpeterek.flightlidar48;

import org.fpeterek.flightlidar48.util.ConfigLoader;


public class Config {

  public final String dbUrl;
  public final String dbUser;
  public final String dbPass;
  public final int metricsPort;

  private Config(String db, String user, String passwd, int prometheusPort) {
    dbUrl = db;
    dbUser = user;
    dbPass = passwd;
    metricsPort = prometheusPort;
  }

  private Config() {
    this(
      ConfigLoader.getString("DB_URL", "jdbc:postgresql://localhost:5432/flightlidar"),
      ConfigLoader.getString("DB_USER", "fpeterek"),
      ConfigLoader.getString("DB_PASSWORD", ""),
      ConfigLoader.getInt("METRICS_PORT", 7779)
    );
  }

  private static Config instance = null;

  public static Config get() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

}
