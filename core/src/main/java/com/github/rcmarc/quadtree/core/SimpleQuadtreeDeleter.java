package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;

public class SimpleQuadtreeDeleter implements QuadtreeDeleter {

    PointChecker pointChecker;
    QuadtreeInserter inserter;
    QuadrantGetter getter;

    public SimpleQuadtreeDeleter(PointChecker pointChecker, QuadtreeInserter inserter, QuadrantGetter getter) {
        this.pointChecker = pointChecker;
        this.inserter = inserter;
        this.getter = getter;
    }

    public SimpleQuadtreeDeleter() {
        QuadtreeConfig config = QuadtreeConfig.getConfig();
        this.pointChecker = config.getPointChecker();
        this.inserter = config.getQuadtreeInserter();
        this.getter = config.getQuadrantGetter();
    }

    @Override
    public boolean delete(Quadtree quadtree, Point2D point) {
        if (quadtree.isLeaf()) {
            return quadtree.getPoints().remove(point);
        }

        final boolean isPointDeleted = getter.getQuadrants(quadtree, point)
                .stream()
                .anyMatch(q -> delete(q, point));

        if (isPointDeleted) {
            if (reduce(quadtree)) {
                quadtree.getOnReduced().ifPresent(action -> action.accept(quadtree));
            }
        }

        return isPointDeleted;
    }

    private boolean reduce(Quadtree quadtree) {
        if (pointChecker.pointsRecursive(quadtree) <= getPointLimit()) {
            Collection<Point2D> points = pointChecker.getPointsRecursive(quadtree);
            Arrays.fill(quadtree.getQuadrants(), null);
            points.forEach(d -> inserter.insert(quadtree, d));
            return true;
        }
        return false;
    }

    private int getPointLimit() {
        return QuadtreeConfig.getConfig().getMaxPoints();
    }

    public static class Builder {
        private PointChecker pointChecker;
        private QuadtreeInserter inserter;
        private QuadrantGetter getter;

        public Builder withPointChecker(PointChecker pointChecker) {
            this.pointChecker = pointChecker;
            return this;
        }

        public Builder withQuadtreeInserter(QuadtreeInserter inserter) {
            this.inserter = inserter;
            return this;
        }

        public Builder withQuadrantGetter(QuadrantGetter getter) {
            this.getter = getter;
            return this;
        }

        private PointChecker getPointChecker() {
            return pointChecker == null ?
                    QuadtreeConfig.getConfig().getPointChecker() :
                    pointChecker;
        }

        private QuadtreeInserter getQuadtreeInserter() {
            return inserter == null ?
                    QuadtreeConfig.getConfig().getQuadtreeInserter() :
                    inserter;
        }

        private QuadrantGetter getQuadrantGetter() {
            return getter == null ?
                    QuadtreeConfig.getConfig().getQuadrantGetter() :
                    getter;
        }

        public SimpleQuadtreeDeleter build() {
            return new SimpleQuadtreeDeleter(
                    getPointChecker(),
                    getQuadtreeInserter(),
                    getQuadrantGetter()
            );
        }
    }
}
