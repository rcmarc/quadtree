package com.github.rcmarc.quadtree.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        if (tree.isLeaf()) return tree.getPoints();

        final List<Point2D> points = new LinkedList<>();

        for (var q : getter.getQuadrants(tree, point)) {
            points.addAll(query(q, point));
        }

        return points;
    }
}
