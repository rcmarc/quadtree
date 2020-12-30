package com.github.rcmarc.quadtree.core;

import java.util.LinkedList;

public class AtomicLeafQuadtree extends AbstractBaseQuadtree {

    private final LinkedList<Data<?>> data = new LinkedList<>();

    public AtomicLeafQuadtree(Point2D dimension, Point2D offset, int depth) {
        super(dimension, offset, Integer.MAX_VALUE, depth,0, false);
    }

    @Override
    public PointContainerChecker getPointChecker() {
        return new ExactPointContainerChecker();
    }

    @Override
    public QuadrantGetter getQuadrantGetter() {
        throw new UnsupportedOperationException(
                String.format("%s is not allowed to be subdivided"
                ,this.getClass().getSimpleName())
        );
    }

    @Override
    public QuadtreeProvider getQuadtreeProvider() {
        return AtomicLeafQuadtree.provider();
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
        return (a,b,c,d,e,f) -> new AtomicLeafQuadtree(a,b,d);
    }
}
