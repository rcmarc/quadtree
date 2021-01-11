package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Bound;
import com.github.rcmarc.quadtree.core.Point2D;

public interface PointGenerator {

    Point2D getPointInBound(Bound bound, double radius, double height, double width);

    Point2D getPoint(double radius, double height, double width);

}
