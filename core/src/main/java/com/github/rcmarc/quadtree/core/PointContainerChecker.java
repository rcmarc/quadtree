package com.github.rcmarc.quadtree.core;

public interface PointContainerChecker {

    /**
     * This method will check if the {@link Point2D} parameter exists on this Quadtree and recursively on the
     * quadrants resulting of the division made in the insert method.
     *
     * @param point the {@link Point2D} to find.
     * @return {@code true} if the {@link Point2D} exists, false otherwise.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     */
    boolean contains(Quadtree tree, Point2D point);
}
