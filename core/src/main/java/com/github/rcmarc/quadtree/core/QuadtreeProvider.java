package com.github.rcmarc.quadtree.core;

@FunctionalInterface
public interface QuadtreeProvider {

    Quadtree getInstance(Point2D dimension, Point2D offset, int maxPoints, int depth, int maxDepth, boolean allowLeaf);

}
