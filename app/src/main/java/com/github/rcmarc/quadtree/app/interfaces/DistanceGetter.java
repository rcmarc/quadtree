package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.core.Point2D;

public interface DistanceGetter {

    double getDistance(Point2D start, Point2D end);

}
