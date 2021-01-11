package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.BoundGetter;
import com.github.rcmarc.quadtree.core.Point2D;

import java.util.Optional;

public class PointRadiusBoundGetter implements BoundGetter {
    @Override
    public Optional<Bound> getBoundIfPointIsIn(Point2D point, double height, double width) {
        if (point.getX() - point.getRadius() <= 0) {
            return Optional.of(Bound.LEFT);
        } else if (point.getX() + point.getRadius() >= width) {
            return Optional.of(Bound.RIGHT);
        } else if (point.getY() + point.getRadius() >= height) {
            return Optional.of(Bound.BOTTOM);
        } else if (point.getY() - point.getRadius() <= 0) {
            return Optional.of(Bound.TOP);
        }
        return Optional.empty();
    }
}
