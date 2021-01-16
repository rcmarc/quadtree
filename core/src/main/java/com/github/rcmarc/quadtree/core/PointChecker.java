package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;

public interface PointChecker {

    default boolean isPointInQuadtreeRecursive(Quadtree quadtree, Point2D point) {
        if (quadtree.isLeaf()) {
            return isPointInQuadtree(quadtree, point);
        }

        return Arrays.stream(quadtree.getQuadrants()).anyMatch(q -> isPointInQuadtree(q, point));
    }

    boolean isPointInQuadtree(Quadtree quadtree, Point2D point);

    Collection<Point2D> getPointsRecursive(Quadtree quadtree);

    int pointsRecursive(Quadtree quadtree);
}
