package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.DistanceGetter;
import com.github.rcmarc.quadtree.core.Point2D;

public class EuclideanDistanceGetter implements DistanceGetter {
    @Override
    public double getDistance(Point2D start, Point2D end) {
        return Math.sqrt(Math.pow(end.getY() - start.getY(), 2) + Math.pow(end.getX() - start.getX(), 2));
    }
}
