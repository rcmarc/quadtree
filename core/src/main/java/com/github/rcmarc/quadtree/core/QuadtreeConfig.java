package com.github.rcmarc.quadtree.core;

public class QuadtreeConfig {
    private static final QuadtreeConfig DEFAULT_CONFIG = new QuadtreeConfig();
    private static QuadtreeConfig ACTIVE_CONFIG = DEFAULT_CONFIG;

    private PointChecker pointChecker;
    private QuadrantGetter quadrantGetter;
    private QuadtreeDeleter quadtreeDeleter;
    private QuadtreeDivider quadtreeDivider;
    private QuadtreeInserter quadtreeInserter;
    private int maxDepth = 3;
    private int maxPoints = 2;

    public static QuadtreeConfig getConfig() {
        return ACTIVE_CONFIG;
    }

    public static void setConfig(QuadtreeConfig config) {
        ACTIVE_CONFIG = config;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public PointChecker getPointChecker() {
        return pointChecker == null ? new SimplePointChecker() : pointChecker;
    }

    public void setPointChecker(PointChecker pointChecker) {
        this.pointChecker = pointChecker;
    }

    public QuadrantGetter getQuadrantGetter() {
        return quadrantGetter == null ? new RadiusQuadrantGetter() : quadrantGetter;
    }

    public void setQuadrantGetter(QuadrantGetter quadrantGetter) {
        this.quadrantGetter = quadrantGetter;
    }

    public QuadtreeDeleter getQuadtreeDeleter() {
        return quadtreeDeleter == null ? new SimpleQuadtreeDeleter() : quadtreeDeleter;
    }

    public void setQuadtreeDeleter(QuadtreeDeleter quadtreeDeleter) {
        this.quadtreeDeleter = quadtreeDeleter;
    }

    public QuadtreeDivider getQuadtreeDivider() {
        return quadtreeDivider == null ? new SimpleQuadtreeDivider() : quadtreeDivider;
    }

    public void setQuadtreeDivider(QuadtreeDivider quadtreeDivider) {
        this.quadtreeDivider = quadtreeDivider;
    }

    public QuadtreeInserter getQuadtreeInserter() {
        return quadtreeInserter == null ? new SimpleQuadtreeInserter() : quadtreeInserter;
    }

    public void setQuadtreeInserter(QuadtreeInserter quadtreeInserter) {
        this.quadtreeInserter = quadtreeInserter;
    }
}
