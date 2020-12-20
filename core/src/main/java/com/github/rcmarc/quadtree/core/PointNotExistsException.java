package com.github.rcmarc.quadtree.core;

public class PointNotExistsException extends RuntimeException{
    public PointNotExistsException(Point2D point) {
        super(String.format("The point %s does not exists", point.toString()));
    }
}
