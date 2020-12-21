package org.fpeterek.flightlidar48.javaapp;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SpriteLoader {
  private static Map<String, Image> map = new HashMap<>();
  private static Lock lock = new ReentrantLock();

  private static Image load(String img) {
    var image = new Image(img);
    map.put(img, image);
    return image;
  }

  public static Image get(String img) {
    try {

      lock.lock();
      var sprite = map.getOrDefault(img, null);

      if (sprite == null) {
        return load(img);
      }
      return sprite;

    } finally {
      lock.unlock();
    }
  }
}
