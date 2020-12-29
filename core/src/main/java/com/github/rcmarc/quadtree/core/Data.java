package com.github.rcmarc.quadtree.core;

public class Data<E> {
    private final E value;
    private final Point2D point;

    public Data(E value, Point2D point){
        this.value = value;
        this.point = point;
    }

    public E getValue() {
        return value;
    }

    public Point2D getPoint() {
        return point;
    }
}
