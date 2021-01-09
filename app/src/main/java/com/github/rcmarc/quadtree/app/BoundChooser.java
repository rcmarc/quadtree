package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;

import java.util.Arrays;
import java.util.Random;

public class BoundChooser {

    double height;
    double width;

    BoundChooser(double height, double width) {
        this.width = width;
        this.height = height;
    }

    Bound getNextBound(Point2D point) {
        return BoundChooser.getNextBound(point, true, height, width);
    }

    Bound getNextBound(Point2D point, boolean isInBounds) {
        return BoundChooser.getNextBound(point, isInBounds, height, width);
    }

    static Bound getNextBound(Point2D point2D, double height, double width) {
        return getNextBound(point2D, true, height, width);
    }

    static Bound getNextBound(Point2D point, boolean isInBounds, double height, double width) {
        if (!isInBounds) {
            return Bound.values()[new Random(System.currentTimeMillis()).nextInt(4)];
        }

        Bound bound = BoundGetter.getBoundIfExists(point, height, width).orElseThrow(IllegalStateException::new);

        return getNextBound(bound);
    }

    static Bound getNextBound(Bound bound) {
        Bound[] bounds = Arrays.stream(Bound.values())
                .filter(b -> !b.equals(bound))
                .toArray(Bound[]::new);

        return bounds[RandomContainer.random.nextInt(bounds.length)];

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

        BoundChooser build() {
            return new BoundChooser(height, width);
        }
    }
}
