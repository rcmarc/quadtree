package com.github.rcmarc.quadtree.core;

import java.util.Collection;

public interface QuadrantGetter {

    Collection<Quadtree> getQuadrants(Quadtree quadtree, Point2D point);


}
