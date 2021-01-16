package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.BoundGetter;
import com.github.rcmarc.quadtree.app.interfaces.NodeAnimator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Collection;

public class BoundCircleAnimator implements NodeAnimator {
    private final BoundGetter boundGetter;
    private Timeline loop;

    public BoundCircleAnimator(BoundGetter boundGetter) {
        this.boundGetter = boundGetter;
    }

    @Override
    public void stop() {
        loop.stop();
    }

    private double randomDelta() {
        return RandomContainer.random.nextDouble() * (RandomContainer.random.nextBoolean() ? 1 : -1);
    }

    @Override
    public void animateAll(Collection<Point2DCircle> particles, double height, double width, Duration duration, Runnable action) {
        loop = new Timeline(new KeyFrame(duration, event -> {
            particles.forEach(p -> animate(p, height, width, duration));
            action.run();
        }));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public void animate(Point2DCircle circle, double height, double width, Duration duration) {
        double x = circle.getPoint().getX() + circle.getDeltaX(), y = circle.getPoint().getY() + circle.getDeltaY();
        circle.setCenterY(height - y);
        circle.setCenterX(x);
        circle.getPoint().setX(x);
        circle.getPoint().setY(y);
        boundGetter.getBoundIfPointIsIn(circle.getPoint(), height, width).ifPresent(bound -> {
            if (bound == Bound.LEFT || bound == Bound.RIGHT) {
                circle.setDeltaX(circle.getDeltaX() * -1);
            } else {
                circle.setDeltaY(circle.getDeltaY() * -1);
            }
        });
    }

    @Override
    public void animate(Point2DCircle circle, double height, double width, Duration duration, Runnable action) {
        final Timeline loop = new Timeline(new KeyFrame(duration, event -> {
            double x = circle.getPoint().getX() + circle.getDeltaX(), y = circle.getPoint().getY() + circle.getDeltaY();
            circle.setLayoutX(circle.getLayoutX() + circle.getDeltaX());
            circle.setLayoutY(circle.getLayoutY() + circle.getDeltaY());
            circle.getPoint().setX(x);
            circle.getPoint().setY(y);
            boundGetter.getBoundIfPointIsIn(circle.getPoint(), height, width).ifPresent(bound -> {
                if (bound == Bound.LEFT || bound == Bound.RIGHT) {
                    circle.setDeltaX(circle.getDeltaX() * -1);
                } else {
                    circle.setDeltaY(circle.getDeltaY() * -1);
                }
            });
            action.run();
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}
