package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;
import javafx.scene.shape.Circle;

public class Point2DCircle extends Circle {

    private final Point2D point;

    public Point2DCircle(Point2D point) {
        super(point.getX(), point.getY(), point.getRadius());
        this.point = point;
    }

    public Point2D getPoint() {
        return point;
    }
}
