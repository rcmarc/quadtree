package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

import static com.github.rcmarc.quadtree.core.Corner.*;

public class Quadtree<E> {

    /**
     * max amount of pints allowed on each quadrant
     */
    int maxPoints;

    /**
     * The current number of data points in the quadrant, if this quadrant is a leaf it will always be zero unless
     * it has a middle data point
     */
    int dataCount = 0;

    /**
     * This is the dimension of the quadrant, x and y represents the width and height
     */
    final Point2D dimension;

    /**
     * The offset of the quadrant, the beginning of the coordinate axis
     */
    final Point2D offset;

    /**
     * This is for the case that a point is in the middle, therefore there is no way to determine to which quadrant
     * should be
     */
    Data<E> middleData;

    Data<E>[] values;

    Quadtree<E>[] quadrants;

    /**
     * @param dimension The dimension of the quadrant, x and y represents the width and height
     * @param offset    The offset of the quadrant, the beginning of the coordinate axis
     * @param maxPoints The max number of data points allowed in the quadtree
     */
    public Quadtree(Point2D dimension, Point2D offset, int maxPoints) {
        this.dimension = dimension;
        this.offset = offset;
        this.maxPoints = maxPoints;
        values = new Data[this.maxPoints];
        quadrants = new Quadtree[4];
    }

    public Quadtree(Point2D dimension, Point2D offset) {
        this(dimension, offset, 1);
    }

    public Quadtree(Point2D dimension) {
        this(dimension, new Point2D(0, 0),1);
    }

    public void insert(Data<E> data) {
        insert(data, true);
    }

    public void insert(Data<E> data, boolean strict) {
        if (isLeaf()) {
            if (strict && contains(data.point)) throw new DuplicatedPointException(data.point);
            if (isFull()) {
                // subdivide
                subdivide();
                // reinsert
                Arrays.stream(values).forEach(this::insertInQuadrant);
                // reset data
                reset();
                // insert the data
                insertInQuadrant(data);
            } else {
                values[dataCount++] = data;
            }

        } else {
            insertInQuadrant(data, true);
        }
    }

    private void insertInQuadrant(Data<E> data) {
        insertInQuadrant(data, false);
    }

    private void insertInQuadrant(Data<E> data, boolean strict) {
        Corner corner = getCorner(data.point);
        if (corner == MIDDLE) {
            if (middleData != null) throw new DuplicatedPointException(data.point);
            middleData = data;
            return;
        }
        quadrants[corner.pos].insert(data, strict);
    }

    public boolean isLeaf() {
        return quadrants[0] == null;
    }

    private boolean isFull() {
        return dataCount == maxPoints;
    }

    Corner getCorner(Point2D point) {
        double max_x = offset.x + dimension.x, max_y = offset.y + dimension.y;

        if (point.x > max_x || point.y > max_y || point.x < offset.x || point.y < offset.y)
            throw new OutsideQuadrantException(point, offset, dimension);

        double middle_x = offset.x + dimension.x / 2, middle_y = offset.y + dimension.y / 2;
        if (point.x == middle_x) {
            if (point.y == middle_y) return MIDDLE;
            return point.y > middle_y ? TOP_LEFT : BOTTOM_RIGHT;
        } else if (point.y == middle_y) {
            return point.x > middle_x ? TOP_RIGHT : BOTTOM_LEFT;
        } else if (point.x > middle_x) {
            return point.y > middle_y ? TOP_RIGHT : BOTTOM_RIGHT;
        }
        return point.y > middle_y ? TOP_LEFT : BOTTOM_LEFT;
    }

    public Quadtree<E> getQuadrant(Point2D point) {
        if (isLeaf()) {
            if (contains(point)) return this;
            return null;
        }
        try {
            return quadrants[getCorner(point).pos].getQuadrant(point);
        }catch (OutsideQuadrantException ignored) {
            return null;
        }
    }

    private void reset() {
        dataCount = middleData == null ? 0 : 1;
        values = null;
    }

    void subdivide() {
        Point2D sub_dimension = dimension.divide(2);
        quadrants[0] = new Quadtree<>(sub_dimension, new Point2D(offset.x, offset.y + dimension.y / 2));
        quadrants[1] = new Quadtree<>(sub_dimension, new Point2D(offset.x + dimension.x / 2, offset.y + dimension.y / 2));
        quadrants[2] = new Quadtree<>(sub_dimension, new Point2D(offset.x + dimension.x / 2, offset.y));
        quadrants[3] = new Quadtree<>(sub_dimension, offset);
    }

    public boolean contains(Point2D point) {
        return Arrays.stream(values).anyMatch(data -> Objects.nonNull(data) && data.point.equals(point));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quadtree<?> quadtree = (Quadtree<?>) o;
        return dimension.equals(quadtree.dimension) && offset.equals(quadtree.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, offset);
    }

}
