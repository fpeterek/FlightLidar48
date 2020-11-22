package org.fpeterek.flightlidar48.validator;

import org.json.JSONException;
import org.json.JSONObject;

public class Validator {

  public void validate(String key, String value) {
    try {
      var json = new JSONObject(value);
    } catch (JSONException e) {

    }
  }

}
