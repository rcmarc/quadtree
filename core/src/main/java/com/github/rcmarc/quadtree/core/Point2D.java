package com.github.rcmarc.quadtree.core;

import java.util.Objects;

public class Point2D implements Comparable<Point2D>{

    double x,y, radius;

    public Point2D(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Point2D(double x, double y) {
        this(x,y,0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public Point2D add(Point2D point){
        return new Point2D(x + point.x, y + point.y, radius);
    }

    public Point2D add(double scalar) {
        return new Point2D(x + scalar, y + scalar, radius);
    }

    public Point2D subtract(Point2D point) {
        return new Point2D(x - point.x, y - point.y, radius);
    }

    public Point2D subtract(double scalar) {
        return new Point2D(x - scalar, y - scalar, radius);
    }

    public Point2D divide(double scalar){
        return new Point2D(x / scalar, y / scalar, radius);
    }

    public Point2D multiply(double scalar) {
        return new Point2D(x * scalar, y * scalar, radius);
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
        return Double.compare(point2D.x, x) == 0 && Double.compare(point2D.y, y) == 0 && Double.compare(point2D.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, radius);
    }


    @Override
    public int compareTo(Point2D o) {
        return o.x == x && o.y == y ? 0 : 1;
    }
    
}
