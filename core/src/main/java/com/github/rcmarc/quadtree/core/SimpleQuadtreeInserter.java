package com.github.rcmarc.quadtree.core;

import java.util.Arrays;

public class SimpleQuadtreeInserter implements QuadtreeInserter {

    PointChecker pointChecker;
    QuadtreeDivider divider;
    QuadrantGetter getter;
    BoundaryHelper helper;

    public SimpleQuadtreeInserter(PointChecker pointChecker, QuadtreeDivider divider, QuadrantGetter getter) {
        this.pointChecker = pointChecker;
        this.divider = divider;
        this.getter = getter;
        helper = new BoundaryHelper();
    }

    public SimpleQuadtreeInserter() {
        QuadtreeConfig config = QuadtreeConfig.getConfig();
        pointChecker = config.getPointChecker();
        divider = config.getQuadtreeDivider();
        getter = config.getQuadrantGetter();
        helper = new BoundaryHelper();
    }

    @Override
    public boolean insert(Quadtree quadtree, Point2D point) {
        return insert(quadtree, point, true);
    }

    private boolean insert(Quadtree quadtree, Point2D point, boolean strict) {
            if (quadtree.isLeaf()) {
                if (strict && pointChecker.isPointInQuadtree(quadtree, point)) {
                    return false;
                }

                if (quadtree.getPoints().size() == getPointLimit()) {

                    if (quadtree.getDepth() == getMaxDepth()) {
                        return quadtree.getPoints().add(point);
                    }

                    divider.divide(quadtree);

                    quadtree.getOnSubdivide().ifPresent(action ->
                            Arrays.stream(quadtree.getQuadrants()).forEach(tree -> tree.onSubdivide(action)));

                    insertPointsInChildren(quadtree);
                    quadtree.clearPoints();

                    getQuadrantsAndInsert(quadtree, point, strict);
                    quadtree.getOnSubdivide().ifPresent(action -> action.accept(quadtree.getQuadrants()));
                } else {
                    if (helper.isPointOverlayingQuadtree(quadtree, point))
                        quadtree.getPoints().add(point);
                    else return false;
                }

            } else {
                getQuadrantsAndInsert(quadtree, point, strict);
            }

        return true;
    }

    private int getPointLimit() {
        return QuadtreeConfig.getConfig().getMaxPoints();
    }

    private int getMaxDepth() {
        return QuadtreeConfig.getConfig().getMaxDepth();
    }

    private void insertPointsInChildren(Quadtree quadtree) {
        quadtree.getPoints().forEach(p -> getQuadrantsAndInsert(quadtree, p));
    }

    private void getQuadrantsAndInsert(Quadtree quadtree, Point2D point) {
        getQuadrantsAndInsert(quadtree, point, false);
    }

    private void getQuadrantsAndInsert(Quadtree quadtree, Point2D point, boolean strict) {
        getter.getQuadrants(quadtree, point).forEach(q -> insert(q, point, strict));
    }

    public static class Builder {
        private PointChecker pointChecker;
        private QuadtreeDivider divider;
        private QuadrantGetter getter;
        private int maxDepth = -1;

        public Builder withPointChecker(PointChecker pointChecker) {
            this.pointChecker = pointChecker;
            return this;
        }

        public Builder withQuadtreeDivider(QuadtreeDivider divider) {
            this.divider = divider;
            return this;
        }

        public Builder withQuadrantGetter(QuadrantGetter getter) {
            this.getter = getter;
            return this;
        }

        public Builder withMaxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        private PointChecker getPointChecker() {
            return pointChecker == null ?
                    QuadtreeConfig.getConfig().getPointChecker() :
                    pointChecker;
        }

        private QuadtreeDivider getDivider() {
            return divider == null ?
                    QuadtreeConfig.getConfig().getQuadtreeDivider() :
                    divider;
        }

        private QuadrantGetter getGetter() {
            return getter == null ?
                    QuadtreeConfig.getConfig().getQuadrantGetter() :
                    getter;
        }

        private int getMaxDepth() {
            return maxDepth == -1 ?
                    QuadtreeConfig.getConfig().getMaxDepth() :
                    maxDepth;
        }

        public SimpleQuadtreeInserter build() {
            return new SimpleQuadtreeInserter(
                    getPointChecker(),
                    getDivider(),
                    getGetter()
            );
        }

    }

}
