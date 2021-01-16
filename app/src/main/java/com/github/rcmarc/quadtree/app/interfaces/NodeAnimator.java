package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Point2DCircle;
import com.github.rcmarc.quadtree.app.config.AppConfig;
import javafx.util.Duration;

import java.util.Collection;

public interface NodeAnimator {

    void animateAll(Collection<Point2DCircle> particles, double height, double width, Duration duration, Runnable action);

    void stop();

    void animate(Point2DCircle circle, double height, double width, Duration duration, Runnable action);

    default void animate(Point2DCircle circle, double height, double width, Duration duration) {
        animate(circle, height, width, duration, () ->{});
    }

    default void animate(Point2DCircle circle) {
        animate(circle, AppConfig.DEFAULT_CONFIG.getHeight(), AppConfig.DEFAULT_CONFIG.getWidth(), AppConfig.DEFAULT_CONFIG.getAnimationDuration());
    }

}
