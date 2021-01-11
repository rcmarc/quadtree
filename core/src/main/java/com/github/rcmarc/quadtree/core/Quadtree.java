package com.github.rcmarc.quadtree.core;

import java.util.function.Consumer;

public interface Quadtree {
    /**
     * This method should return the max number of {@link Data} instances allowed
     * to be stored on the Quadtree, this number is equal to the max number of {@link Data}
     * allowed plus the {@code Data} in middle point of this Quadtree
     *
     * @return The max number of {@link Data} instances allowed
     */
    int getMaxDataAllowed();

    /**
     * This method should return the number of elements on this Quadtree, this number is
     * equal to the number of elements plus the {@link Data} in middle point if {@code getMiddleData != null}
     *
     * @return The amount of {@link Data} on this Quadtree
     */
    int getDataCount();

    /**
     *
     * @return the max depth allowed by this {@link Quadtree}
     */
    int getMaxDepth();

    /**
     *
     * @return the current depth of this {@link Quadtree}
     */
    int getDepth();


    /**
     * This method should return the dimension of this Quadtree, which is a {@link Point2D}
     * where the x field represents the width and the y field represents the height
     *
     * @return The dimension of this Quadtree
     */
    Point2D getDimension();

    /**
     * This method should return the offset of this Quadtree, which is the starting point of this
     * Quadtree on the coordinate axis
     *
     * @return The offset of this Quadtree
     */
    Point2D getOffset();

    /**
     * This method should return all the quadrants on this Quadtree, if this Quadtree is a leaf
     * this method will return an array with all his elements null
     *
     * @return All the quadrants off the Quadtree
     */
    Quadtree[] getQuadrants();

    /**
     * This method will insert the {@link Data} on this Quadtree and it will make the subdivision
     * once {@code getDataCount() == getMaxDataAllowed()}. <br/>
     *
     * @param data The data to insert.
     */
    boolean insert(Data<?> data);

    /**
     * This method will delete the {@link Data} located on the {@link Point2D} parameter
     *
     * @param point The point to delete.
     */
    boolean delete(Point2D point);

    /**
     * This method will return all values inside the quadtree including the ones that are inside only by the radius size
     * @return the values of this Quadtree
     */
    Data<?>[] getAllData();

    /**
     * The quadtree is leaf if all his sub quadrants are null, but this method only should check for null of one
     * of his sub quadrants cause for definition a quadtree has four sub quadrants or zero.
     *
     * @return {@code true} if this Quadtree is leaf, {@code false} otherwise.
     */
    default boolean isLeaf() {
        return getQuadrants()[0] == null;
    }

    /**
     * The Quadtree is empty if {@code isLeaf() == true} and has no data stored in it.
     *
     * @return {@code true} if is empty, false otherwise.
     */
    default boolean isEmpty() {
        return getDataCount() == 0  && isLeaf();
    }

    /**
     *
     * @return {@code true} if this {@link Quadtree} allows to be subdivided in four {@link AtomicLeafQuadtree}
     */
    boolean allowLeaf();

    void onSubdivide(Consumer<Quadtree[]> consumer);

    void onReduced(Consumer<Quadtree> consumer);
}
