package com.github.rcmarc.quadtree.core;

public class OutsideQuadrantException extends RuntimeException {
    public OutsideQuadrantException(Point2D point, Point2D offset, Point2D dimension) {
        super(String.format("The point %s is outside the quadrant, min x: %f, max x: %f, min y: %f, max y: %f"
                , point.toString()
                , offset.x, offset.x + dimension.x
                , offset.y, offset.y + dimension.y
        ));
    }
}
