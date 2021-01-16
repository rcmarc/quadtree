package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.core.Point2D;
import com.github.rcmarc.quadtree.visual.JavaFXHelper;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Point2DCircle extends Circle {

    private final Point2D point;
    private final Color initialColor;

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    private double deltaX;
    private double deltaY;

    public Point2DCircle(Point2D point, Color initialColor) {
        super(point.getX(), point.getY(), point.getRadius());
//        setLayoutX(point.getX());
//        setLayoutY(point.getY());
        this.point = point;
        this.initialColor = initialColor;
        this.deltaX = randomDelta();
        this.deltaY = randomDelta();
    }

    public Color getInitialColor() {
        return initialColor;
    }

    private double randomDelta() {
        return RandomContainer.random.nextDouble() * (RandomContainer.random.nextBoolean() ? 1 : -1);
    }

    public Point2D getPoint() {
        return point;
    }
}
