package com.github.rcmarc.quadtree.core;

public class DuplicatedPointException extends RuntimeException{
    DuplicatedPointException(Point2D point){
        super(String.format("The Point %s already exists", point.toString()));
    }
}
