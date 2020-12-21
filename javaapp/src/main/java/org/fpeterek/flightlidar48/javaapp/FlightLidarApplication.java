package org.fpeterek.flightlidar48.javaapp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.fpeterek.flightlidar48.util.GeoPoint;

import java.util.List;


public class FlightLidarApplication extends Application {

  private Stage stage;
  private Group root;
  private Canvas canvas;
  private GraphicsContext gc;
  private Image background;
  private final ApiClient api = new ApiClient();
  private List<Aircraft> aircraft = null;

  private final double left = -41.8560533;
  private final double right = 126.8060561;
  private final double top = 70.8958181;
  private final double bottom = 16.4487911;

  private final GeoPoint lb = new GeoPoint(bottom, left + 360.0);
  private final GeoPoint rt = new GeoPoint(top, right);

  private static final double updateThreshold = 1.0;
  private double timeSinceUpdate = 0.0;

  public double width() {
    return root.getScene().getWindow().getWidth();
  }

  public double height() {
    return root.getScene().getWindow().getHeight();
  }

  public boolean isOpen() {
    return stage != null;
  }

  public void close() {
    stage.close();
    stage = null;
  }

  public void clear() {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    gc.drawImage(background, 0, 0);
  }

  private Image getSprite(Aircraft ac) {
    var dir = (int)Math.round(ac.direction);
    var dist = dir % 15;
    dist = (dist <= 7) ? (-dist) : (15-dist);

    var spriteNum = (dir + dist) % 360;
    var spritePath = "sprites/" + spriteNum + ".png";
    return SpriteLoader.get(spritePath);
  }

  private Point calcPosition(Aircraft ac) {

    var lat = ac.lat();
    var lon = (ac.lon() > 180) ? (ac.lon() - 360.0) : ac.lon();

    var x = (lon - left) / ((right - left) / width());
    var y = height() - ((lat - bottom) / ((top - bottom) / height()));

    return new Point((int)Math.round(x-16), (int)Math.round(y-16));
  }

  public void render() {
    if (aircraft == null) {
      return;
    }

    aircraft.forEach( ac -> {
      var pos = calcPosition(ac);

      gc.setFill(Color.BLACK);
      gc.fillRect(pos.x() - 12, pos.y() + 30, 56, 16);

      gc.setTextAlign(TextAlignment.CENTER);
      gc.setTextBaseline(VPos.CENTER);
      gc.setFill(Color.RED);
      gc.fillText(ac.registration, pos.x() + 16, pos.y() + 38);
    });

    aircraft.forEach( ac -> {
      var sprite = getSprite(ac);
      var pos = calcPosition(ac);
      gc.drawImage(sprite, pos.x(), pos.y());
    });
  }

  private void estimate(double dt) {
    if (aircraft == null) {
      return;
    }

    for (var ac : aircraft) {
      ac.calcEstimate(dt);
    }
  }

  private void performUpdate(double dt) {
    try {
      aircraft = api.get(lb, rt);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      estimate(dt);
    }
  }

  public void update(double dt) {
    clear();

    timeSinceUpdate += dt;
    if (timeSinceUpdate >= updateThreshold) {
      timeSinceUpdate = 0.0;
      performUpdate(dt);
    }

    render();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;

    root = new Group();
    canvas = new Canvas(1600, 900);

    stage.setScene(new Scene(root));
    root.getChildren().add(canvas);
    stage.setTitle("FlightLidar48");

    gc = canvas.getGraphicsContext2D();

    background = new Image("map.png");

    stage.show();

    new AnimationTimer() {

      private double start = System.currentTimeMillis();

      @Override
      public void handle(long l) {

        final double now = System.currentTimeMillis();
        final double dt = (now - start) / 1000.0;

        update(dt);

        if (!isOpen()) {
          this.stop();
        }

        start = now;
      }
    }.start();
  }
}
