package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ExactPointContainerChecker implements PointContainerChecker {

    private final InclusiveQuadrantGetter getter;

    public ExactPointContainerChecker() {
        this.getter = new InclusiveQuadrantGetter();
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

    @Override
    public Optional<? extends Data<?>> getDataIfExists(Quadtree tree, Point2D point) {
        if (!tree.isEmpty()) {
            if (tree.isLeaf()) {
                return Arrays.stream(tree.getAllData())
                        .filter(Objects::nonNull)
                        .filter(data -> data.getPoint().equals(point))
                        .findFirst();
            }
            return Arrays.stream(tree.getQuadrants())
                    .map(q -> getDataIfExists(q, point).orElse(null))
                    .filter(Objects::nonNull)
                    .findFirst();
        }
        return Optional.empty();
    }

}
