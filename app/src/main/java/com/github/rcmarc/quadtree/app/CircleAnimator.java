package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class CircleAnimator {

    private static double euclideanDistance(Point2D start, Point2D destination) {
        return Math.sqrt(
                Math.pow(destination.getY() - start.getY(), 2) + Math.pow(destination.getX() - start.getX(), 2)
        );
    }

    private static Duration getDuration(double distance, double speed) {
        return Duration.seconds(distance / speed);
    }

    static void animate(Point2DCircle circle, double height, double width, double speed) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(circle);

        moveToNextBound(circle, transition, height, width, speed);

        transition.setOnFinished(event -> moveToNextBound(circle, transition, height, width, speed));

        circle.localToSceneTransformProperty().addListener((observable, oldValue, newValue) -> {
            final Bounds bounds = circle.localToScene(circle.getBoundsInLocal());
            circle.getPoint().setX(bounds.getMinX() + circle.getPoint().getRadius());
            circle.getPoint().setY(bounds.getMinY() + circle.getPoint().getRadius());
        });
    }

    static void moveToNextBound(Point2DCircle circle, TranslateTransition transition,double height, double width, double speed) {
        BoundGetter.getBoundIfExists(circle.getPoint(),height, width).ifPresentOrElse((bound -> {
            Point2D point = BoundCoordinateHelper.getRandomPointInBound(
                    BoundChooser.getNextBound(bound),
                    circle.getPoint().getRadius(),
                    height, width
            );
            transition.setDuration(getDuration(euclideanDistance(circle.getPoint(), point), speed));

            moveToPoint(point, transition);
        }
        ),() -> {

        });
    }

    static void moveToPoint(Point2D point, TranslateTransition transition) {
        transition.setToX(point.getX());
        transition.setToY(point.getY());
        transition.play();
    }

}
