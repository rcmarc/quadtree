package com.github.rcmarc.quadtree.core;

import java.util.Collection;

public interface QuadtreeQuery {

    Collection<Point2D> query(Quadtree tree, Point2D point);

    long pointsInRange(Quadtree tree, Point2D point);

    boolean arePointsInRange(Quadtree tree, Point2D point);

}
