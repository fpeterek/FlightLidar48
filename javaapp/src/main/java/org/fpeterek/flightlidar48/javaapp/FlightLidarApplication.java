package org.fpeterek.flightlidar48.javaapp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;


public class FlightLidarApplication extends Application {

  private Stage stage;
  private Group root;
  private Canvas canvas;
  private GraphicsContext gc;
  private Image background;

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

  public void render(Renderable r) {
    r.render(gc);
  }

  private void performUpdate() {

  }

  public void update(double dt) {
    clear();

    timeSinceUpdate += dt;
    if (timeSinceUpdate >= updateThreshold) {
      timeSinceUpdate = 0.0;
      performUpdate();
    }
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
