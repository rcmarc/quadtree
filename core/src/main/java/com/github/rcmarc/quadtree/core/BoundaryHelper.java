package com.github.rcmarc.quadtree.core;

public class BoundaryHelper {
    public boolean isPointOverlayingQuadtree(Quadtree tree, Point2D point) {
        if (isPointBetweenHorizontalEdges(tree, point)) {
            if (isPointBetweenVerticalEdges(tree, point)){
                return true;
            }
            if (point.getY() > getMaxY(tree)) {
                return (point.getY() - point.getRadius()) <= getMaxY(tree);
            }
            return (point.getY() + point.getRadius()) >= getMinY(tree);
        }
        if (isPointBetweenVerticalEdges(tree, point)) {
            if (point.getX() > getMaxX(tree)) {
                return (point.getX() - point.getRadius()) <= getMaxX(tree);
            }
            return (point.getX() + point.getRadius()) >= getMinX(tree);
        }
        return false;
    }

    /**
     *
     * @param tree the {@link Quadtree} to check in.
     * @param point the {@link Point2D} to check.
     * @return {@code true} if the {@link Point2D} is between the horizontal edges of the {@link Quadtree} regardless
     * of the {@code point.getX()} value.
     */
    public boolean isPointBetweenHorizontalEdges(Quadtree tree, Point2D point) {
        return (getMinX(tree) <= (point.getX() + point.getRadius())) &&
                (getMaxX(tree) >= (point.getX() - point.getRadius()));
    }

    /**
     *
     * @param tree the {@link Quadtree} to check in.
     * @param point the {@link Point2D} to check.
     * @return {@code true} if the {@link Point2D} is between the vertical edges of the {@link Quadtree} regardless
     * of the {@code point.getX()} value.
     */
    public boolean isPointBetweenVerticalEdges(Quadtree tree, Point2D point) {
        return (getMinY(tree) <= (point.getY() + point.getRadius())) &&
                (getMaxY(tree) >= (point.getY() - point.getRadius()));
    }

    /**
     *
     * @param tree the {@link Quadtree} to check
     * @param point the {@code Point2D} to check
     * @return {@code true} if the {@link Point2D} is outside the {@link Quadtree}, {@code false} otherwise
     */
    public boolean isPointOutsideQuadtree(Quadtree tree, Point2D point) {
        return getMinX(tree) > point.getX() ||
                getMaxX(tree) < point.getX() ||
                getMinY(tree) > point.getY() ||
                getMaxY(tree) < point.getY();
    }

    /**
     *
     * @param tree the {@link Quadtree} to check
     * @param point the {@link Point2D} to check
     * @return {@code true} if the {@link Point2D} is inside the {@link Quadtree}, {@code false} otherwise
     */
    public boolean isPointInsideQuadtree(Quadtree tree, Point2D point) {
        return !isPointOutsideQuadtree(tree, point);
    }

    /**
     *
     * @param tree the {@link Quadtree}
     * @return the min horizontal value that this tree allow
     */
    public double getMinX(Quadtree tree) {
        return tree.getOffset().getX();
    }

    /**
     *
     * @param tree the {@link Quadtree}
     * @return the max horizontal value that this tree allow
     */
    public double getMaxX(Quadtree tree) {
        return tree.getOffset().getX() + tree.getDimension().getX();
    }

    /**
     *
     * @param tree the {@link Quadtree}
     * @return the min vertical value that this tree allow
     */
    public double getMinY(Quadtree tree) {
        return tree.getOffset().getY();
    }

    /**
     *
     * @param tree the {@link Quadtree}
     * @return the max vertical value that this tree allow
     */
    public double getMaxY(Quadtree tree) {
        return tree.getOffset().getY() + tree.getDimension().getY();
    }
}
