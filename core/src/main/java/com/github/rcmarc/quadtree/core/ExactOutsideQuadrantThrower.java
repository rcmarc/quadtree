package com.github.rcmarc.quadtree.core;

public class ExactOutsideQuadrantThrower implements OutsideQuadrantThrower{
    @Override
    public void throwIfOutsideQuadrant(Quadtree tree, Point2D point) {
        double min_x = tree.getOffset().getX();
        if (point.getX() >= min_x) {
            double max_x = tree.getOffset().getX() + tree.getDimension().getX();
            if (point.getX() <= max_x) {
                double min_y = tree.getOffset().getY();
                if (point.getY() >= min_y) {
                    double max_y = tree.getOffset().getY() + tree.getDimension().getY();
                    if (point.getY() <= max_y) return;
                }
            }
        }
        throw new OutsideQuadrantException(point, tree.getOffset(), tree.getDimension());
    }
}
