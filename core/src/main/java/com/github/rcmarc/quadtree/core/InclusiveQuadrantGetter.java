package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class InclusiveQuadrantGetter implements QuadrantGetter{
    @Override
    public Collection<Quadtree> getQuadrants(Quadtree tree, Point2D point) {
        Point2D offset = tree.getOffset(), dimension = tree.getDimension();
        double max_x = offset.getX() + dimension.getX(), max_y = offset.getY() + dimension.getY();

        if (point.getX() > max_x || point.getY() > max_y || point.getX() < offset.getX() || point.getY() < offset.getY())
            return Collections.emptyList();

        double middle_x = offset.getX() + dimension.getX() / 2, middle_y = offset.getY() + dimension.getY() / 2;

        Quadtree[] quadrants = tree.getQuadrants();

        if (point.getX() == middle_x) {
            if (point.getY() == middle_y) return Arrays.asList(quadrants);
            return point.getY() > middle_y ?
                    Arrays.asList(quadrants[0], quadrants[1]):
                    Arrays.asList(quadrants[2], quadrants[3]);
        }
        if (point.getY() == middle_y) {
            return point.getX() > middle_x ?
                    Arrays.asList(quadrants[1], quadrants[2]) :
                    Arrays.asList(quadrants[0], quadrants[3]);
        }
        if (point.getX() > middle_x) {
            return point.getY() > middle_y ?
                    Collections.singleton(quadrants[1]) :
                    Collections.singleton(quadrants[2]);
        }
        return point.getY() > middle_y ?
                Collections.singleton(quadrants[0]) :
                Collections.singleton(quadrants[3]);
    }
}
