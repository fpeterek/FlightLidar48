package org.fpeterek.flightlidar48;
/*
import org.fpeterek.flightlidar48.database.CountryGateway;
import org.fpeterek.flightlidar48.records.Country;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WebApp {

  @RequestMapping(value="/", method=RequestMethod.GET)
  public String index() {
    return "html/webapp";
  }

  @RequestMapping(value="/flights", method=RequestMethod.GET)
  public String flights(
    @RequestParam(value="leftTop") String leftTop,
    @RequestParam(value="rightBottom") String rightBottom
  ) {
    System.out.println("latlon: {" + leftTop + ", " + rightBottom + "}");
    return "{\"flights\": []}";
  }

  @RequestMapping(value="/countries", method=RequestMethod.GET)
  public String countries() {

    List<Country> list = new ArrayList<>();

    try {
      final var gw = new CountryGateway();
      list = gw.get();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }

    var array = new JSONArray();
    list.stream().map(Country::json).forEach(array::put);

    return new JSONObject().put("countries", array).toString();
  }

}
*/