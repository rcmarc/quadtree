package com.github.rcmarc.quadtree.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RegionQuadtree extends Quadtree {

    List<Point2D> points;
    QuadtreeInserter inserter;
    QuadtreeDeleter deleter;
    QuadtreeQuery query;

    public RegionQuadtree(Point2D dimension, Point2D offset, int depth) {
        super(dimension, offset, depth);
        points = new LinkedList<>();
        inserter = new SimpleQuadtreeInserter(
                new SimplePointChecker(),
                new SimpleQuadtreeDivider(),
                new RadiusQuadrantGetter()
        );
        deleter = new SimpleQuadtreeDeleter(
                new SimplePointChecker(),
                new SimpleQuadtreeInserter(),
                new RadiusQuadrantGetter()
        );
        query = new SimpleQuadtreeQuery();
    }

    public RegionQuadtree(Point2D dimension, int depth) {
        this(dimension, new Point2D(0, 0), depth);
    }

    @Override
    public QuadtreeQuery getQuery() {
        return query;
    }

    @Override
    protected QuadtreeDeleter getQuadtreeDeleter() {
        return deleter;
    }

    @Override
    protected QuadtreeInserter getQuadtreeInserter() {
        return inserter;
    }

    @Override
    public Collection<Point2D> getPoints() {
        return points;
    }

    @Override
    protected QuadtreeProvider getProvider() {
        return RegionQuadtree::new;
    }

    public void setInserter(QuadtreeInserter inserter) {
        this.inserter = inserter;
    }

    public void setDeleter(QuadtreeDeleter deleter) {
        this.deleter = deleter;
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

        public RegionQuadtree build() {
            return new RegionQuadtree(dimension, offset, depth);
        }

    }
}
