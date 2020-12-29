package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

public class ExactPointContainerChecker implements PointContainerChecker {

    private final ExactOutsideQuadrantThrower thrower;
    private final ExactQuadrantGetter getter;

    public ExactPointContainerChecker() {
        this.thrower = new ExactOutsideQuadrantThrower();
        this.getter = new ExactQuadrantGetter();
    }


    @Override
    public boolean contains(Quadtree tree, Point2D point) {
        try {
            thrower.throwIfOutsideQuadrant(tree, point);
        } catch (OutsideQuadrantException ignored) {
            return false;
        }

        return containsR(tree, point);
    }

    private boolean containsR(Quadtree tree, Point2D point) {

        if (tree.isEmpty()) return false;

        if (tree.isLeaf()) {
            return Arrays.stream(tree.getAllData())
                    .filter(Objects::nonNull)
                    .map(Data::getPoint)
                    .anyMatch(p -> p.equals(point));
        }

        return containsR(getter.getQuadrants(tree, point).get(0),point);
    }

}
