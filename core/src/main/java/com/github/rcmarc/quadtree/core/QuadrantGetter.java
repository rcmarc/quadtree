package com.github.rcmarc.quadtree.core;

import java.util.List;

public interface QuadrantGetter {

    /**
     * This method will return the quadrants that the {@link Point2D} parameter belongs to.
     *
     * @param point the point to look for.
     * @return the Quadtree that the {@link Point2D} parameter belongs to
     */
    List<Quadtree> getQuadrants(Quadtree tree, Point2D point);

}
