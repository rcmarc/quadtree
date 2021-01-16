package com.github.rcmarc.quadtree.core;

@FunctionalInterface
public interface QuadtreeProvider {

    Quadtree provide(Point2D dimension, Point2D offset, int depth);

    default Quadtree provide(Point2D dimension, int depth) {
        return provide(dimension, new Point2D(0,0), depth);
    }

}
