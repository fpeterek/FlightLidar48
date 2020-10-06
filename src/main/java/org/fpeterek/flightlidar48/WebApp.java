package org.fpeterek.flightlidar48;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebApp {

  @RequestMapping(value="/", method= RequestMethod.GET)
  public String index() {
    return "html/webapp";
  }

}
