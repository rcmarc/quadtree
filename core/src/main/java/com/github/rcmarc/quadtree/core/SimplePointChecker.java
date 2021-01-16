package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

public class SimplePointChecker implements PointChecker{
    @Override
    public boolean isPointInQuadtree(Quadtree quadtree, Point2D point) {
        return quadtree.getPoints().contains(point);
    }

    @Override
    public Collection<Point2D> getPointsRecursive(Quadtree quadtree) {
        if (quadtree.isLeaf()) {
            return quadtree.getPoints();
        }
        LinkedList<Point2D> list = new LinkedList<>();
        Arrays.stream(quadtree.getQuadrants()).forEach(q -> list.addAll(getPointsRecursive(q)));
        return list;
    }

    @Override
    public int pointsRecursive(Quadtree quadtree) {
        if (quadtree.isLeaf()) {
            return quadtree.getPoints().size();
        }
        return Arrays.stream(quadtree.getQuadrants()).mapToInt(this::pointsRecursive).sum();
    }
}
