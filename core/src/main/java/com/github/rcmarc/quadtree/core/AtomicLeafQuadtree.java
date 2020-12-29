package com.github.rcmarc.quadtree.core;

import java.util.LinkedList;

public class AtomicLeafQuadtree extends AbstractBaseQuadtree {

    private final LinkedList<Data<?>> data = new LinkedList<>();

    public AtomicLeafQuadtree(Point2D dimension, Point2D offset) {
        super(dimension, offset, Integer.MAX_VALUE, 0, false);
    }

    @Override
    public PointContainerChecker getPointChecker() {
        return new ExactPointContainerChecker();
    }

    @Override
    public QuadrantGetter getQuadrantGetter() {
        return new ExactQuadrantGetter();
    }

    @Override
    public QuadtreeProvider getQuadtreeProvider() {
        return (a,b,c,d,e) -> new AtomicLeafQuadtree(a,b);
    }

    @Override
    public Data<?>[] getAllData() {
        return data.toArray(Data[]::new);
    }

    @Override
    public void setData(Data<?> data, int index) {
        if (data == null) {
            this.data.remove(index);
        } else {
            this.data.add(index, data);
        }
    }

    @Override
    public Data<?> getData(int index) {
        return data.get(index);
    }

    @Override
    protected void clearCollection() {
        data.clear();
    }

    static QuadtreeProvider provider() {
        return (a,b,c,d,e) -> new AtomicLeafQuadtree(a,b);
    }
}
