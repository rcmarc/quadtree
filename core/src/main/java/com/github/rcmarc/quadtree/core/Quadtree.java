package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Objects;

public interface Quadtree<E> {
    /**
     * This method should return the max number of {@link Data} instances allowed
     * to be stored on the Quadtree, this number is equal to the max number of {@link Data}
     * allowed plus the {@code Data} in middle point of this Quadtree
     * @return The max number of {@link Data} instances allowed
     */
    int getMaxDataAllowed();

    /**
     * This method should return the number of elements on this Quadtree, this number is
     * equal to the number of elements plus the {@link Data} in middle point if {@code getMiddleData != null}
     * @return The amount of {@link Data} on this Quadtree
     */
    int getDataCount();

    /**
     * This method should return the {@link Corner} that a {@link Point2D} belongs to.
     * @param point the point.
     * @return the {@link Corner} that the {@link Point2D} belongs to.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     * @throws PointNotExistsException if the {@link Point2D} doesn't exist.
     */
    Corner getCorner(Point2D point);

    /**
     * This method should return the dimension of this Quadtree, which is a {@link Point2D}
     * where the x field represents the width and the y field represents the height
     * @return The dimension of this Quadtree
     */
    Point2D getDimension();

    /**
     * This method should return the offset of this Quadtree, which is the starting point of this
     * Quadtree on the coordinate axis
     * @return The offset of this Quadtree
     */
    Point2D getOffset();

    /**
     * This method should return all the quadrants on this Quadtree, if this Quadtree is a leaf
     * this method will return an array with all his elements null
      * @return All the quadrants off the Quadtree
     */
    Quadtree<E>[] getQuadrants();

    /**
     * This method will insert the {@link Data} on this Quadtree and it will make the subdivision
     * once {@code getDataCount() == getMaxDataAllowed()}. <br/>
     * @param data The data to insert.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     * @throws DuplicatedPointException if the {@link Point2D} already is in this Quadtree.
     */
    void insert(Data<E> data);

    /**
     * This method will delete the {@link Data} located on the {@link Point2D} parameter
     * @param point The point to delete.
     * @throws PointNotExistsException if the {@link Point2D} doesn't exist.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     */
    void delete(Point2D point);

    /**
     * This method will return an array containing all the values of this Quadtree including the {@code getMiddleData()}
     * @return the values of this Quadtree
     */
    Data<E>[] getValues();

    /**
     * This method will return the value in the middle off this Quadtree, this is necessary
     * cause there is no way of distribute this value to the quadrants resulting of the division of
     * the Quadtree.
     * @return The middle data.
     */
    Data<E> getMiddleData();

    /**
     * This method will return the quadrant that the {@link Point2D} parameter belongs to.
     * @param point the point to look for.
     * @return the Quadtree that the {@link Point2D} parameter belongs to
     * @throws PointNotExistsException if the {@link Point2D} doesn't exist on this Quadtree.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     */
    Quadtree<E> getQuadrant(Point2D point);

    /**
     * This method will check if the {@link Point2D} parameter exists on this Quadtree and recursively on the
     * quadrants resulting of the division made in the insert method.
     * @param point the {@link Point2D} to find.
     * @return {@code true} if the {@link Point2D} exists, false otherwise.
     * @throws OutsideQuadrantException if the {@link Point2D} is outside of the boundaries of this Quadtree.
     */
    boolean contains(Point2D point);

    /**
     * The quadtree is leaf if all his sub quadrants are null, but this method only should check for null of one
     * of his sub quadrants cause for definition a quadtree has four sub quadrants or zero.
     * @return {@code true} if this Quadtree is leaf, {@code false} otherwise.
     */
    default boolean isLeaf() {
        return Arrays.stream(getQuadrants()).allMatch(Objects::isNull);
    }

    /**
     * The Quadtree is empty if {@code isLeaf() == true} and has no data stored in it.
     * @return {@code true} if is empty, false otherwise.
     */
    default boolean isEmpty(){
        return getDataCount() == 0 && getMiddleData() == null && isLeaf();
    }

    /**
     * This method will check if the point is inside the boundaries of the {@link Quadtree} parameter
     * and therefore can be inserted.
     * @param point the point to check.
     * @param tree the tree to check in.
     * @throws OutsideQuadrantException if the point is outside of the {@link Quadtree} parameter.
     */
    static void checkBoundaries(Point2D point, Quadtree<?> tree){
        double min_x = tree.getOffset().getX();
        if (point.getX() >= min_x) {
            double max_x = tree.getOffset().getX() + tree.getDimension().getX();
            if (point.getX() <= max_x) {
                double min_y = tree.getOffset().getY();
                if (point.getY() >= min_y) {
                    double max_y = tree.getOffset().getY() + tree.getDimension().getY();
                    if (point.getY() <= max_y) return;
                }
            }
        }
        throw new OutsideQuadrantException(point, tree.getOffset(), tree.getDimension());
    }
}
