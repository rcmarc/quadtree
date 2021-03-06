package com.github.rcmarc.quadtree.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PointQuadtree extends Quadtree{

    List<Point2D> points;
    QuadtreeDeleter deleter;
    QuadtreeInserter inserter;
    QuadtreeQuery query;

    public PointQuadtree(Point2D dimension, Point2D offset, int depth) {
        super(dimension, offset, depth);
        points = new LinkedList<>();
        inserter = new SimpleQuadtreeInserter(
                new SimplePointChecker(),
                new SimpleQuadtreeDivider(),
                new ExactQuadrantGetter()
        );
        deleter = new SimpleQuadtreeDeleter(
                new SimplePointChecker(),
                new SimpleQuadtreeInserter(),
                new ExactQuadrantGetter()
        );
        query = new SimpleQuadtreeQuery();
    }

    public PointQuadtree(Point2D dimension, int depth) {
        this(dimension, new Point2D(0, 0), depth);
    }

    public PointQuadtree(Point2D dimension, Point2D offset) {
        this(dimension, offset, 0);
    }

    public PointQuadtree(Point2D dimension) {
        this(dimension, 0);
    }

    @Override
    public Collection<Point2D> getPoints() {
        return points;
    }

    @Override
    protected QuadtreeInserter getQuadtreeInserter() {
        return inserter;
    }

    @Override
    protected QuadtreeDeleter getQuadtreeDeleter() {
        return deleter;
    }

    @Override
    protected QuadtreeProvider getProvider() {
        return PointQuadtree::new;
    }

    @Override
    public QuadtreeQuery getQuery() {
        return query;
    }

    public static class Builder {
        private Point2D dimension;
        private Point2D offset;
        private int depth;

        public Builder withDimension(Point2D dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder withOffset(Point2D offset) {
            this.offset = offset;
            return this;
        }

        public Builder withDepth(int depth) {
            this.depth = depth;
            return this;
        }

        public PointQuadtree build() {
            return new PointQuadtree(dimension, offset, depth);
        }

    }
}
