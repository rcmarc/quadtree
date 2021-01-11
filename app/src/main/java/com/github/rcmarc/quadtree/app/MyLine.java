package com.github.rcmarc.quadtree.app;

import javafx.scene.shape.Line;

public class MyLine extends Line {
    public MyLine(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            MyLine objLine = (MyLine) obj;
            return objLine.getStartX() == getStartX() &&
                    objLine.getStartY() == getStartY() &&
                    objLine.getEndX() == getEndX() &&
                    objLine.getEndY() == getEndY();
        } catch (ClassCastException ex) {
            return false;
        }
    }
}
