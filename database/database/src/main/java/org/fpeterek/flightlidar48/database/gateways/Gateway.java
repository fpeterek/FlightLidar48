package org.fpeterek.flightlidar48.database.gateways;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Gateway {

  protected Connection conn;
  protected abstract String baseQuery();
  protected abstract Object extractOne(ResultSet resultSet) throws SQLException;

  protected List<Object> getAll() throws SQLException {

    final var stmt = conn.createStatement();
    final var res = new ArrayList<Object>();
    final var sql = baseQuery() + ";";

    var set = stmt.executeQuery(sql);

    while(set.next()) {
      res.add(extractOne(set));
    }

    return res;
  }

  public Gateway() throws SQLException {
    conn = DriverManager.getConnection(
      "jdbc:postgresql://localhost:5432/flightlidar",
      "fpeterek",
      ""
    );
  }

  public Gateway(String url, String user, String password) throws SQLException {
    conn = DriverManager.getConnection(url, user, password);
  }

}
