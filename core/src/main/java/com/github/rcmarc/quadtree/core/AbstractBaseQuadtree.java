package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

import static com.github.rcmarc.quadtree.core.Corner.*;

public abstract class AbstractBaseQuadtree<E> implements Quadtree<E> {

    protected void subdivide(){
        Point2D dimension = getDimension();
        Point2D sub_dimension = dimension.divide(2), offset = getOffset();
        int maxPoints = getMaxDataAllowed();
        Quadtree<E>[] quadrants = getQuadrants();
        quadrants[0] = new NoDepthLimitQuadtree<>(sub_dimension, new Point2D(offset.x, offset.y + dimension.y / 2), maxPoints);
        quadrants[1] = new NoDepthLimitQuadtree<>(sub_dimension, new Point2D(offset.x + dimension.x / 2, offset.y + dimension.y / 2), maxPoints);
        quadrants[2] = new NoDepthLimitQuadtree<>(sub_dimension, new Point2D(offset.x + dimension.x / 2, offset.y), maxPoints);
        quadrants[3] = new NoDepthLimitQuadtree<>(sub_dimension, offset, maxPoints);
    }

    @Override
    public Corner getCorner(Point2D point) {
        Point2D offset = getOffset(), dimension = getDimension();
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

    @Override
    public boolean contains(Point2D point) {
        try {
            Quadtree.checkBoundaries(point, this);

            if (isEmpty()) return false;

            if (isLeaf()) {
                return Arrays.stream(getValues()).anyMatch(data -> data.point.equals(point));
            }

            return getQuadrants()[getCorner(point).pos].contains(point);
        } catch (OutsideQuadrantException ignored) {
            return false;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseQuadtree<?> quadtree = (AbstractBaseQuadtree<?>) o;
        return getDimension().equals(quadtree.getDimension()) && getOffset().equals(quadtree.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDimension(), getOffset());
    }

}
