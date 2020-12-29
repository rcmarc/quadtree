package com.github.rcmarc.quadtree.core;

public interface OutsideQuadrantThrower {
    /**
     * This method will check if the point is inside the boundaries of the {@link Quadtree} parameter
     * and therefore can be inserted.
     *
     * @param point the point to check.
     * @param tree  the tree to check in.
     * @throws OutsideQuadrantException if the point is outside of the {@link Quadtree} parameter.
     */
    void throwIfOutsideQuadrant(Quadtree tree, Point2D point);

}
