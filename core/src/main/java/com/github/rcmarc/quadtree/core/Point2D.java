package com.github.rcmarc.quadtree.core;

import java.util.Objects;

public class Point2D implements Comparable<Point2D>{

    final double x,y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D add(Point2D point){
        return new Point2D(x + point.x, y + point.y);
    }

    public Point2D divide(double scalar){
        return new Point2D(x / scalar, y / scalar);
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 && Double.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Point2D o) {
        return o.x == x && o.y == y ? 0 : 1;
    }
    
}
