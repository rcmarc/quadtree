package com.github.rcmarc.quadtree.core;

import java.util.Arrays;


public class PointQuadtree extends AbstractBaseQuadtree {

    Data<?>[] data;

    public PointQuadtree(Point2D dimension) {
        this(dimension, new Point2D(0,0));
    }

    public PointQuadtree(Point2D dimension, Point2D offset) {
        this(dimension, offset, 1);
    }

    public PointQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed) {
        this(dimension, offset, maxDataAllowed, Integer.MAX_VALUE, false);
    }

    public PointQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed, int maxDepth, boolean allowLeaf) {
        super(dimension, offset, maxDataAllowed, maxDepth, allowLeaf);
        data = new Data[maxDataAllowed];
    }

    @Override
    protected PointContainerChecker getPointChecker() {
        return new ExactPointContainerChecker();
    }

    @Override
    protected QuadrantGetter getQuadrantGetter() {
        return new ExactQuadrantGetter();
    }

    @Override
    protected QuadtreeProvider getQuadtreeProvider() {
        return PointQuadtree::new;
    }

    @Override
    protected void clearCollection() {
        Arrays.fill(data, null);
    }

    @Override
    public Data<?>[] getAllData() {
        return data;
    }

    @Override
    public void setData(Data<?> data, int index) {
        this.data[index] = data;
    }

    @Override
    public Data<?> getData(int index) {
        return data[index];
    }
}
