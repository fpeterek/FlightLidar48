package org.fpeterek.flightlidar48;

import io.prometheus.client.Counter;

public class Metrics {

  public static Counter dbCalls = Counter.build()
    .name("database_calls_total")
    .help("Total number of calls to database")
    .register();

  public static Counter failedCalls = Counter.build()
    .name("failed_calls_total")
    .help("Total number of failed calls")
    .register();

}
