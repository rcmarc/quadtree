package com.github.rcmarc.quadtree.core;

public interface PointContainerChecker {

    /**
     * This method will check if the {@link Point2D} parameter exists on this Quadtree and recursively on the
     * quadrants resulting of the division made in the insert method.
     *
     * @param point the {@link Point2D} to find.
     * @return {@code true} if the {@link Point2D} exists, false otherwise.
     */
    boolean contains(Quadtree tree, Point2D point);
}
