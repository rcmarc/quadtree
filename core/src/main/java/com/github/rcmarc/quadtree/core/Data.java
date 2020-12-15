package com.github.rcmarc.quadtree.core;

public class Data<E> {
    E value;
    Point2D point;

    public Data(E value, Point2D point){
        this.value = value;
        this.point = point;
    }
}
