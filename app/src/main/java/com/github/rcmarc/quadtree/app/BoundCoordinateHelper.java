package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;

public class BoundCoordinateHelper {

    static Point2D getRandomPointInBound(Bound bound,double radius, double height, double width) {
        return switch (bound) {
            case BOTTOM -> new Point2D(RandomContainer.nextDouble(0, width - radius * 2),height - radius * 2);
            case TOP -> new Point2D(RandomContainer.nextDouble(0, width - radius * 2), 0);
            case LEFT -> new Point2D(0, RandomContainer.nextDouble(0, height - radius * 2));
            case RIGHT -> new Point2D(width - radius * 2, RandomContainer.nextDouble(0, height - radius * 2));
        };
    }

}
