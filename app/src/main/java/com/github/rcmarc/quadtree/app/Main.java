package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Main extends Application {

    final double radius = 5;
    final double width = 1200;
    final double height = 600;
    final double speed = 700;

    public void start(Stage stage) {

        Point2DCircle[] circles = new Point2DCircle[300];
        IntStream.range(0, circles.length).forEach(i -> circles[i] = getCircle(radius,radius, radius));

        Group root = new Group(circles);
        Scene scene = new Scene(root, width, height);
        stage.setTitle("Translate Example");
        stage.setScene(scene);
        stage.show();

        Arrays.stream(circles).forEach(c -> CircleAnimator.animate(c, height,width, speed));
    }

    static Point2DCircle getCircle(double x, double y, double radius) {
        Point2DCircle circle = new Point2DCircle(new Point2D(x,y,radius));
        circle.setFill(randomColor());
        return circle;
    }

    static Color randomColor() {
        return Color.color(RandomContainer.nextDouble(0,1),
                RandomContainer.nextDouble(0,1),
                RandomContainer.nextDouble(0, 1));
    }

    public static void main(String[] args) {
        launch(args);
    }
}