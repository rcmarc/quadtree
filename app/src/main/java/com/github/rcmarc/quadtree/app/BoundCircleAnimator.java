package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.BoundGetter;
import com.github.rcmarc.quadtree.app.interfaces.NodeAnimator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class BoundCircleAnimator implements NodeAnimator {
    private final BoundGetter boundGetter;

    public BoundCircleAnimator(BoundGetter boundGetter) {
        this.boundGetter = boundGetter;
    }

    private double randomDelta() {
        return RandomContainer.random.nextDouble() * (RandomContainer.random.nextBoolean() ? 1 : -1);
    }

    @Override
    public void animate(Point2DCircle circle, double height, double width, Duration duration) {
        final Timeline loop = new Timeline(new KeyFrame(duration, new EventHandler<>() {
            double deltaX = randomDelta();
            double deltaY = randomDelta();

            @Override
            public void handle(ActionEvent event) {
                double x = circle.getPoint().getX() + deltaX, y = circle.getPoint().getY() + deltaY;
                circle.setLayoutX(circle.getLayoutX() + deltaX);
                circle.setLayoutY(circle.getLayoutY() + deltaY);
                circle.getPoint().setX(x);
                circle.getPoint().setY(y);
                boundGetter.getBoundIfPointIsIn(circle.getPoint(), height, width).ifPresent(bound -> {
                    if (bound == Bound.LEFT || bound == Bound.RIGHT) {
                        deltaX *= -1;
                    } else {
                        deltaY *= -1;
                    }
                });
            }
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
}
