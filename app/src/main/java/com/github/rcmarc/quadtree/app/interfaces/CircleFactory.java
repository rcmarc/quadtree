package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Point2DCircle;
import com.github.rcmarc.quadtree.core.Point2D;

@FunctionalInterface
public interface CircleFactory {

    Point2DCircle buildCircle(Point2D point);

}
