package com.github.rcmarc.quadtree.core;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Quadtree {

    private Consumer<Quadtree[]> onSubdivideAction;
    private Consumer<Quadtree> onReducedAction;
    private final Quadtree[] quadrants;
    private final Point2D dimension;
    private final Point2D offset;
    private final int depth;

    public Quadtree(Point2D dimension, Point2D offset, int depth) {
        this.depth = depth;
        this.offset = offset;
        this.dimension = dimension;
        quadrants = new Quadtree[4];
    }

    public abstract Collection<Point2D> getPoints();

    protected abstract QuadtreeInserter getQuadtreeInserter();

    protected abstract QuadtreeDeleter getQuadtreeDeleter();

    protected abstract QuadtreeProvider getProvider();

    protected abstract QuadtreeQuery getQuery();

    public Collection<Point2D> query(Point2D point) {
        return getQuery().query(this, point);
    }

    Quadtree genUpperLeftQuadrant() {
        return getProvider().provide(
                getDimension().divide(2),
                new Point2D(getOffset().getX(), getOffset().getY() + getDimension().getY() / 2),
                getDepth() + 1
        );
    }

    Quadtree genUpperRightQuadrant() {
        return getProvider().provide(
                getDimension().divide(2),
                new Point2D(getOffset().getX() + getDimension().getX() / 2, getOffset().getY() + getDimension().getY() / 2),
                getDepth() + 1
        );
    }

    Quadtree genLowerLeftQuadrant() {
        return getProvider().provide(
                getDimension().divide(2),
                getOffset(),
                getDepth() + 1
        );
    }

    Quadtree genLowerRightQuadrant() {
        return getProvider().provide(
                getDimension().divide(2),
                new Point2D(getOffset().getX() + getDimension().getX() / 2, getOffset().getY()),
                getDepth() + 1
        );
    }

    public boolean insert(Point2D point) {
        return getQuadtreeInserter().insert(this, point);
    }

    public boolean delete(Point2D point) {
        return getQuadtreeDeleter().delete(this, point);
    }

    protected void clearPoints() {
        getPoints().clear();
    }

    public Point2D getDimension() {
        return dimension;
    }

    public Point2D getOffset() {
        return offset;
    }

    public Quadtree[] getQuadrants() {
        return quadrants;
    }

    public boolean isLeaf() {
        return getQuadrants()[0] == null;
    }

    public void onSubdivide(Consumer<Quadtree[]> action) {
        onSubdivideAction = action;
    }

    public void onReduced(Consumer<Quadtree> action) {
        onReducedAction = action;
    }

    public int getDepth() {
        return depth;
    }

    public Optional<Consumer<Quadtree[]>> getOnSubdivide() {
        return Optional.ofNullable(onSubdivideAction);
    }

    public Optional<Consumer<Quadtree>> getOnReduced() {
        return Optional.ofNullable(onReducedAction);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quadtree quadtree = (Quadtree) o;
        return dimension.equals(quadtree.dimension) && offset.equals(quadtree.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, offset);
    }
}
