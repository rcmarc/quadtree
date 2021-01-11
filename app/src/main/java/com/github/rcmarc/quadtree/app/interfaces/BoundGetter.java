package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Bound;
import com.github.rcmarc.quadtree.core.Point2D;

import java.util.Optional;

public interface BoundGetter {

    Optional<Bound> getBoundIfPointIsIn(Point2D point, double height, double width);

}
