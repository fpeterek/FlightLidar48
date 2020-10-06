package org.fpeterek.flightlidar48;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
