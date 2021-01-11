package com.github.rcmarc.quadtree.app.config;

import com.github.rcmarc.quadtree.app.*;
import com.github.rcmarc.quadtree.app.interfaces.*;
import javafx.util.Duration;

public class AppConfig {

    private CircleFactory circleFactory = new RandomColorCircleBuilder();
    private DistanceGetter distanceGetter = new EuclideanDistanceGetter();
    private BoundChooser boundChooser = new SimpleBoundChooser();
    private BoundGetter boundGetter = new PointRadiusBoundGetter();
    private PointGenerator pointGenerator = new RandomPointGenerator();
    private NodeAnimator nodeAnimator = new BoundCircleAnimator(getBoundGetter());
    private QuadtreeDrawer quadtreeDrawer = new SimpleQuadtreeDrawer();
    private Duration animationDuration = Duration.millis(3);
    private double speed = 400;
    private double height = 600;
    private double width = 1200;
    private double radius = 5;
    public static AppConfig DEFAULT_CONFIG = new AppConfig();

    public void setCircleFactory(CircleFactory circleFactory) {
        this.circleFactory = circleFactory;
    }

    public void setDistanceGetter(DistanceGetter distanceGetter) {
        this.distanceGetter = distanceGetter;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public CircleFactory getCircleFactory() {
        return circleFactory;
    }

    public DistanceGetter getDistanceGetter() {
        return distanceGetter;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getRadius() {
        return radius;
    }

    public BoundChooser getBoundChooser() {
        return boundChooser;
    }

    public void setBoundChooser(BoundChooser boundChooser) {
        this.boundChooser = boundChooser;
    }

    public BoundGetter getBoundGetter() {
        return boundGetter;
    }

    public void setBoundGetter(BoundGetter boundGetter) {
        this.boundGetter = boundGetter;
    }

    public PointGenerator getPointGenerator() {
        return pointGenerator;
    }

    public void setPointGenerator(PointGenerator pointGenerator) {
        this.pointGenerator = pointGenerator;
    }

    public NodeAnimator getNodeAnimator() {
        return nodeAnimator;
    }

    public void setNodeAnimator(NodeAnimator nodeAnimator) {
        this.nodeAnimator = nodeAnimator;
    }

    public Duration getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Duration animationDuration) {
        this.animationDuration = animationDuration;
    }

    public QuadtreeDrawer getQuadtreeDrawer() {
        return quadtreeDrawer;
    }

    public void setQuadtreeDrawer(QuadtreeDrawer quadtreeDrawer) {
        this.quadtreeDrawer = quadtreeDrawer;
    }
}
