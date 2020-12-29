package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

public class ExactPointContainerChecker implements PointContainerChecker {

    private final ExactQuadrantGetter getter;

    public ExactPointContainerChecker() {
        this.getter = new ExactQuadrantGetter();
    }


    @Override
    public boolean contains(Quadtree tree, Point2D point) {
        if (tree.isEmpty()) return false;

        if (tree.isLeaf()) {
            return Arrays.stream(tree.getAllData())
                    .filter(Objects::nonNull)
                    .map(Data::getPoint)
                    .anyMatch(p -> p.equals(point));
        }

        return contains(getter.getQuadrants(tree, point).get(0),point);
    }

}
