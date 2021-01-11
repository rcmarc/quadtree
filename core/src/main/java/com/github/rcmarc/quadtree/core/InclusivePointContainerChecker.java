package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class InclusivePointContainerChecker implements PointContainerChecker{
    private final InclusiveQuadrantGetter getter;

    public InclusivePointContainerChecker() {
        this.getter = new InclusiveQuadrantGetter();
    }


    @Override
    public boolean contains(Quadtree tree, Point2D point) {
        if (tree.isEmpty()) return false;

        if (tree.isLeaf()) {
            return Arrays.stream(tree.getAllData())
                    .filter(Objects::nonNull)
                    .map(Data::getPoint)
                    .anyMatch(p -> isIn(point, p));
        }

        return contains(getter.getQuadrants(tree, point).get(0),point);
    }

    @Override
    public Optional<? extends Data<?>> getDataIfExists (Quadtree tree, Point2D point) {
        if (!tree.isEmpty()) {
            if (tree.isLeaf()) {
                return Arrays.stream(tree.getAllData())
                        .filter(Objects::nonNull)
                        .filter(data -> isIn(point, data.getPoint()))
                        .findFirst();
            }
            return Arrays.stream(tree.getQuadrants())
                    .map(q -> getDataIfExists(q, point).orElse(null))
                    .filter(Objects::nonNull)
                    .findFirst();
        }
        return Optional.empty();
    }

    private boolean isIn(Point2D point1, Point2D point2) {
        return (point2.getX() + point2.getRadius()) >= point1.getX() &&
                (point2.getX() - point2.getRadius()) <= point1.getX() &&
                (point2.getY() + point2.getRadius()) >= point1.getY() &&
                (point2.getY() - point2.getRadius()) <= point1.getY();
    }
}
