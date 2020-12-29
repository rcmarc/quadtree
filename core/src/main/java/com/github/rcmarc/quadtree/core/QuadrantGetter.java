package com.github.rcmarc.quadtree.core;

import java.util.List;

public interface QuadrantGetter {

    /**
     * This method will return the quadrants that the {@link Point2D} parameter belongs to.
     *
     * @param point the point to look for.
     * @return the Quadtree that the {@link Point2D} parameter belongs to
     * @throws PointNotExistsException  if the {@link Point2D} doesn't exist on this Quadtree.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     */
    List<Quadtree> getQuadrants(Quadtree tree, Point2D point);

}
