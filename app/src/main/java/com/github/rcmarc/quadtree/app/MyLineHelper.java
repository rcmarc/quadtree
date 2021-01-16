package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Point2D;
import com.github.rcmarc.quadtree.core.Quadtree;
import com.github.rcmarc.quadtree.visual.JavaFXHelper;

public class MyLineHelper {

    public MyLine getTopBottomLine(Quadtree tree, double height) {
        Point2D offset = tree.getOffset(), dimension = tree.getDimension();
        double x = offset.getX() + dimension.getX() / 2;
        return new MyLine(
                x, height - offset.getY(),
                x, height - (offset.getY() + dimension.getY())
        );
    }

    public MyLine getLeftRightLine(Quadtree tree, double height) {
        Point2D offset = tree.getOffset(), dimension = tree.getDimension();
        double y = height - (offset.getY() + dimension.getY() / 2);
        return new MyLine(
                offset.getX(), y,
                offset.getX() + dimension.getX(), y
        );
    }
}
