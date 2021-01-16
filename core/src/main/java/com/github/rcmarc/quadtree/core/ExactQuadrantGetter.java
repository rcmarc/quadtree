package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ExactQuadrantGetter implements QuadrantGetter{
    @Override
    public Collection<Quadtree> getQuadrants(Quadtree tree, Point2D point) {
        Point2D offset = tree.getOffset(), dimension = tree.getDimension();
        double max_x = offset.getX() + dimension.getX(), max_y = offset.getY() + dimension.getY();

        if (point.getX() > max_x || point.getY() > max_y || point.getX() < offset.getX() || point.getY() < offset.getY())
            return Collections.emptyList();

        double middle_x = offset.getX() + dimension.getX() / 2, middle_y = offset.getY() + dimension.getY() / 2;

        if (point.getX() == middle_x) {
            if (point.getY() == middle_y) return Arrays.asList(tree.getQuadrants());
            return point.getY() > middle_y ?
                    Collections.singleton(tree.getQuadrants()[0]) :
                    Collections.singleton(tree.getQuadrants()[2]);
        } else if (point.getY() == middle_y) {
            return point.getX() > middle_x ?
                    Collections.singleton(tree.getQuadrants()[1]) :
                    Collections.singleton(tree.getQuadrants()[3]);
        } else if (point.getX() > middle_x) {
            return point.getY() > middle_y ?
                Collections.singleton(tree.getQuadrants()[1]) :
                Collections.singleton(tree.getQuadrants()[2]);
        }

        return point.getY() > middle_y ?
                Collections.singleton(tree.getQuadrants()[0]) :
                Collections.singleton(tree.getQuadrants()[3]);
    }
}
