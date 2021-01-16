package com.github.rcmarc.quadtree.core;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleQuadtreeQuery implements QuadtreeQuery{

    QuadrantGetter getter;
    BoundaryHelper helper;

    public SimpleQuadtreeQuery(){
        getter = new RadiusQuadrantGetter();
        helper = new BoundaryHelper();
    }

    @Override
    public Collection<Point2D> query(Quadtree tree, Point2D point) {
        if (!helper.isPointOverlayingQuadtree(tree, point)) return Collections.emptyList();
        if (tree.isLeaf())
            return tree.getPoints().stream()
                    .filter(p -> isInRange(point, p))
                    .collect(Collectors.toUnmodifiableList());

        final List<Point2D> points = new LinkedList<>();

        for (var q : getter.getQuadrants(tree, point)) {
            points.addAll(query(q, point));
        }

        return points;
    }

    public boolean arePointsInRange(Quadtree tree, Point2D point) {
        if (tree.isLeaf()) {
            for (Point2D p : tree.getPoints()) {
                if (!p.equals(point) && isInRange(point, p)) {
                    return true;
                }
            }
            return false;
        }

        for (Quadtree q : getter.getQuadrants(tree, point)) {
            if (arePointsInRange(q, point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long pointsInRange(Quadtree tree, Point2D point) {
        if (!helper.isPointOverlayingQuadtree(tree, point)) return 0;
        if (tree.isLeaf())
            return tree.getPoints().stream()
                    .filter(p -> isInRange(point, p))
                    .count();

        long count = 0;
        for (var q : getter.getQuadrants(tree, point)) {
            count += pointsInRange(q, point);
        }

        return count;
    }

    private boolean isInRange(Point2D range, Point2D point) {
        return point.getX() <= range.getX() + range.getRadius() * 2 &&
                point.getX() >= range.getX() - range.getRadius() * 2 &&
                point.getY() <= range.getY() + range.getRadius() * 2 &&
                point.getY() >= range.getY() - range.getRadius() * 2;
    }
}
