package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;

import java.util.Optional;

public class BoundGetter {

    double height;
    double width;

    BoundGetter(double height, double width) {
        this.height = height;
        this.width = width;
    }

    Optional<Bound> getBoundIfExists(Point2D point) {
        return BoundGetter.getBoundIfExists(point, height, width);
    }

    static Optional<Bound> getBoundIfExists(Point2D point, double height, double width) {
        if (point.getX() - point.getRadius() == 0) {
            return Optional.of(Bound.LEFT);
        } else if (point.getX() + point.getRadius() == width) {
            return Optional.of(Bound.RIGHT);
        } else if (point.getY() + point.getRadius() == height) {
            return Optional.of(Bound.BOTTOM);
        } else if (point.getY() - point.getRadius() == 0) {
            return Optional.of(Bound.TOP);
        }
        return Optional.empty();
    }

    static class Builder {

        double width;
        double height;

        Builder withWidth(double width) {
            this.width = width;
            return this;
        }

        Builder withHeight(double height) {
            this.height = height;
            return this;
        }

        BoundGetter build() {
            return new BoundGetter(height, width);
        }
    }
}
