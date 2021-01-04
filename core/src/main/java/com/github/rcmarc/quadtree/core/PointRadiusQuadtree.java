package com.github.rcmarc.quadtree.core;


import java.util.Arrays;

public class PointRadiusQuadtree extends AbstractBaseQuadtree {

    private final Data<?>[] data;

    public PointRadiusQuadtree(Point2D dimension) {
        this(dimension, new Point2D(0,0));
    }

    public PointRadiusQuadtree(Point2D dimension, Point2D offset) {
        this(dimension, offset, 1);
    }
    public PointRadiusQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed) {
        this(dimension, offset, maxDataAllowed,0, Integer.MAX_VALUE, false);
    }

    public PointRadiusQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed,int depth, int maxDepth, boolean allowLeaf) {
        super(dimension, offset, maxDataAllowed,depth, maxDepth, allowLeaf);
        data = new Data[maxDataAllowed];
    }

    @Override
    public PointContainerChecker getPointChecker() {
        return new ExactPointContainerChecker();
    }

    @Override
    public QuadrantGetter getQuadrantGetter() {
        return new RadiusQuadrantGetter();
    }

    @Override
    public QuadtreeProvider getQuadtreeProvider() {
        return PointRadiusQuadtree::new;
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

    @Override
    protected void clearCollection() {
        Arrays.fill(data, null);
    }

}
