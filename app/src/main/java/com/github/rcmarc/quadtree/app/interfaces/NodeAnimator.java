package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Point2DCircle;
import com.github.rcmarc.quadtree.app.config.AppConfig;
import javafx.util.Duration;

public interface NodeAnimator {

    void animate(Point2DCircle circle, double height, double width, Duration duration);

    default void animate(Point2DCircle circle) {
        animate(circle, AppConfig.DEFAULT_CONFIG.getHeight(), AppConfig.DEFAULT_CONFIG.getWidth(), AppConfig.DEFAULT_CONFIG.getAnimationDuration());
    }

}
