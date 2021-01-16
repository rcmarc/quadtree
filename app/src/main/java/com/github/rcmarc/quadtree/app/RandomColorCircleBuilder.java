package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.CircleFactory;
import com.github.rcmarc.quadtree.core.Point2D;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class RandomColorCircleBuilder implements CircleFactory {
    @Override
    public Point2DCircle buildCircle(Point2D point) {
        Color color = randomColor();
        Point2DCircle circle = new Point2DCircle(point, color);
        circle.setFill(color);
        return circle;
    }

    private Color randomColor() {
        return Color.color(RandomContainer.nextDouble(0,1),
                RandomContainer.nextDouble(0,1),
                RandomContainer.nextDouble(0, 1));
    }
}
