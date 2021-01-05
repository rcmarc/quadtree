package com.github.rcmarc.quadtree.core;


import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.IntStream;

public abstract class AbstractBaseQuadtree implements Quadtree {

    private final Point2D dimension;
    private final Point2D offset;
    private final int maxDataAllowed;
    private final int maxDepth;
    private final PointContainerChecker pointChecker;
    private final QuadtreeDivider divider;
    private final QuadrantGetter quadrantGetter;
    private final Quadtree[] quadrants;
    private final PointGetter pointGetter;
    private final boolean allowLeaf;

    private int dataCount = 0;
    private int depth;

    public AbstractBaseQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed, int maxDepth, boolean allowLeaf) {
        this(dimension,offset,maxDataAllowed, 0, maxDepth, allowLeaf);
    }

    AbstractBaseQuadtree(Point2D dimension, Point2D offset, int maxDataAllowed,int depth, int maxDepth, boolean allowLeaf) {
        this.dimension = dimension;
        this.offset = offset;
        this.maxDataAllowed = maxDataAllowed;
        this.maxDepth = maxDepth;
        quadrants = new Quadtree[4];
        this.depth = depth;
        pointChecker = getPointChecker();
        divider = new QuadtreeDivider(getQuadtreeProvider());
        quadrantGetter = getQuadrantGetter();
        this.allowLeaf = allowLeaf;
        pointGetter = new PointGetter();
    }

    @Override
    public int getMaxDataAllowed() {
        return maxDataAllowed;
    }

    @Override
    public int getDataCount() {
        return dataCount;
    }

    @Override
    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public Point2D getDimension() {
        return dimension;
    }

    @Override
    public Point2D getOffset() {
        return offset;
    }

    @Override
    public Quadtree[] getQuadrants() {
        return quadrants;
    }

    @Override
    public boolean allowLeaf() {
        return allowLeaf;
    }

    @Override
    public boolean insert(Data<?> data) {
        return insert(data, true);
    }

    public boolean insertAll(Data<?>... collection){
        try {
            if(!Arrays.stream(collection).allMatch(this::insert)) throw new Exception();
            return true;
        } catch (Exception ignored) {
            Arrays.stream(collection).map(Data::getPoint).forEach(this::delete);
        }
        return false;
    }

    private boolean insert(Data<?> data, boolean strict) {
        if (isLeaf()) {
            if (strict && pointChecker.contains(this, data.getPoint()))
                return false;
            if (getDataCount() == getMaxDataAllowed()) {

                if (getDepth() == getMaxDepth()) {
                    return false;
                }

                divider.subdivide(this);

                onSubdivide();

                getQuadrantsAndInsert(data, true);
            } else {
                setData(data, dataCount++);
            }
        } else {
            getQuadrantsAndInsert(data, true);
        }
        return true;
    }

    protected void onSubdivide() {
        depth++;
        reinsertAll();
        clearData();
    }

    private void reinsertAll() {
        Arrays.stream(getAllData())
                .filter(Objects::nonNull)
                .forEach(this::getQuadrantsAndInsert);
    }

    private void getQuadrantsAndInsert(Data<?> data) {
        getQuadrantsAndInsert(data, false);
    }

    private void getQuadrantsAndInsert(Data<?> data, boolean strict) {
        quadrantGetter.getQuadrants(this, data.getPoint())
                .stream()
                .map(q -> ((AbstractBaseQuadtree) q))
                .forEach(q -> q.insert(data, strict));
    }

    @Override
    public boolean delete(Point2D point) {
        if (isLeaf()) {
            try {
                int index = IntStream.range(0, dataCount)
                        .filter(i -> getData(i).getPoint().equals(point))
                        .findFirst().orElseThrow();

                setData(null, index);
                dataCount--;
            } catch (NoSuchElementException ignored) {
                return false;
            }
            return true;
        }

        final boolean isPointDeleted = getQuadrantsAndDelete(point);

        if (isPointDeleted) {
           final boolean wereQuadrantsDeleted = deleteIfEmpty();
           if (!wereQuadrantsDeleted) tryToReduce();
        }
        return isPointDeleted;
    }

    private void tryToReduce() {
        if (pointGetter.getDataSize(this) <= getMaxDataAllowed()) {
            List<Data<?>> list = pointGetter.getAllData(this);
            Arrays.fill(quadrants, null);
            list.forEach(d -> insert(d, false));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBaseQuadtree that = (AbstractBaseQuadtree) o;
        return dimension.equals(that.dimension) && offset.equals(that.offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimension, offset);
    }

    private boolean deleteIfEmpty() {
        final boolean areAllQuadrantsEmpty =  Arrays.stream(quadrants).allMatch(Quadtree::isEmpty);
        if (areAllQuadrantsEmpty) Arrays.fill(quadrants, null);
        return areAllQuadrantsEmpty;
    }

    private boolean getQuadrantsAndDelete(Point2D point) {
        return quadrantGetter.getQuadrants(this, point)
                .stream()
                .anyMatch(q -> q.delete(point));
    }

    private void clearData() {
        dataCount = 0;
        clearCollection();
    }

    protected void setQuadtreeProvider(QuadtreeProvider provider) {
        divider.setProvider(provider);
    }

    protected abstract PointContainerChecker getPointChecker();

    protected abstract QuadrantGetter getQuadrantGetter();

    protected abstract QuadtreeProvider getQuadtreeProvider();

    protected abstract void clearCollection();

    protected abstract void setData(Data<?> data, int index);

    protected abstract Data<?> getData(int index);

}
