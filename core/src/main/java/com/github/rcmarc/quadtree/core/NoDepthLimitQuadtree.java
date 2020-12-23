package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

import static com.github.rcmarc.quadtree.core.Corner.*;

public class NoDepthLimitQuadtree<E> extends AbstractBaseQuadtree<E> {

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

    /**
     * This is the index of the array position of the middleData;
     */
    int middleDataIndex;

    Data<E>[] values;

    NoDepthLimitQuadtree<E>[] quadrants;

    /**
     * @param dimension The dimension of the quadrant, x and y represents the width and height
     * @param offset    The offset of the quadrant, the beginning of the coordinate axis
     * @param maxPoints The max number of data points allowed in the quadtree
     */
    public NoDepthLimitQuadtree(Point2D dimension, Point2D offset, int maxPoints) {
        this.dimension = dimension;
        this.offset = offset;
        this.maxPoints = maxPoints;
        values = new Data[this.maxPoints + 1];
        quadrants = new NoDepthLimitQuadtree[4];
    }

    public NoDepthLimitQuadtree(Point2D dimension, Point2D offset) {
        this(dimension, offset, 1);
    }

    public NoDepthLimitQuadtree(Point2D dimension) {
        this(dimension, new Point2D(0, 0), 1);
    }

    @Override
    public void insert(Data<E> data) {
        insert(data, true);
    }

    @Override
    public NoDepthLimitQuadtree<E>[] getQuadrants() {
        return quadrants;
    }

    @Override
    public Point2D getOffset() {
        return offset;
    }

    @Override
    public int getMaxDataAllowed() {
        return middleData == null ? maxPoints : maxPoints + 1;
    }

    @Override
    public int getDataCount() {
        return dataCount;
    }

    public Point2D getDimension() {
        return dimension;
    }

    public void insert(Data<E> data, boolean strict) {
        if (isLeaf()) {
            if (strict && contains(data.point, this)) throw new DuplicatedPointException(data.point);
            if (getDataCount() == getMaxDataAllowed()) {
                // subdivide
                subdivide();
                // reinsert
                Arrays.stream(values).filter(Objects::nonNull).forEach(this::insertInQuadrant);
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

    public void delete(Point2D point) {
        if (isLeaf()) {
            if (middleData != null) {
                if (middleData.point.equals(point)) {
                    setMiddleData(null);
                    return;
                }
            }

            for (int i = 0; i < values.length; i++) {
                if (values[i].point.equals(point)) {
                    values[i] = null;
                    dataCount--;
                    return;
                }
            }
            throw new PointNotExistsException(point);
        }
        deleteInQuadrant(point);

        deleteIfEmpty();
    }

    @Override
    public Data<E>[] getValues() {
        return values;
    }

    @Override
    public Data<E> getMiddleData() {
        return middleData;
    }

    private void deleteIfEmpty() {
        if (Arrays.stream(quadrants).allMatch(NoDepthLimitQuadtree::isEmpty)) Arrays.fill(quadrants, null);
    }

    private void deleteInQuadrant(Point2D point) {
        Corner corner = getCorner(point);
        if (corner == MIDDLE) {
            if (middleData == null) throw new PointNotExistsException(point);
            setMiddleData(null);
            return;
        }

        quadrants[corner.pos].delete(point);
    }

    private void setMiddleData(Data<E> data) {
        middleData = data;
        values[dataCount] = data;
        middleDataIndex = data == null ? -1 : dataCount;
        dataCount = data == null ? dataCount - 1 : dataCount + 1;
    }

    private void insertInQuadrant(Data<E> data) {
        insertInQuadrant(data, false);
    }

    private void insertInQuadrant(Data<E> data, boolean strict) {
        Corner corner = getCorner(data.point);
        if (corner == MIDDLE) {
            if (middleData != null) throw new DuplicatedPointException(data.point);
            setMiddleData(data);
            return;
        }
        quadrants[corner.pos].insert(data, strict);
    }

    @Override
    public NoDepthLimitQuadtree<E> getQuadrant(Point2D point) {
        if (isLeaf()) {
            if (contains(point, this)) return this;
            return null;
        }
        try {
            return quadrants[getCorner(point).pos].getQuadrant(point);
        } catch (OutsideQuadrantException ignored) {
            return null;
        }
    }

    private void reset() {
        dataCount = 0;
        values = null;
        middleDataIndex = -1;
    }

    private boolean contains(Point2D point, Quadtree<?> tree) {
        return Arrays.stream(tree.getValues()).anyMatch(data -> Objects.nonNull(data) && data.point.equals(point));
    }

}
