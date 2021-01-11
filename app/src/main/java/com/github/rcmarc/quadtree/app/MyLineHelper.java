package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Quadtree;
import com.github.rcmarc.quadtree.visual.JavaFXHelper;

public class MyLineHelper {

    private Quadtree tree;
    private double offsetX;
    private double offsetY;
    private double dimensionX;
    private double dimensionY;

    public MyLineHelper(Quadtree tree) {
        this.tree = tree;
        initAttrs();
    }

    private void initAttrs() {
        offsetY = tree.getOffset().getY();
        offsetX = tree.getOffset().getX();
        dimensionX = tree.getDimension().getX();
        dimensionY = tree.getDimension().getY();
    }

    public void setTree(Quadtree tree) {
        this.tree = tree;
        initAttrs();
    }

    public MyLine getBottomTopLine() {
        return new MyLine(
                (int) (offsetX + dimensionX / 2),
                (int) JavaFXHelper.getY(offsetY, offsetY + dimensionY),
                (int) (offsetX + dimensionX / 2),
                (int) offsetY
        );
    }

    public MyLine getLeftRightLine() {
        return new MyLine(
                (int) offsetX,
                (int) JavaFXHelper.getY(offsetY + dimensionY / 2, offsetY + dimensionY),
                (int) (offsetX + dimensionX),
                (int) JavaFXHelper.getY(offsetY + dimensionY / 2, offsetY + dimensionY)
        );
    }

    public static MyLine getLeftRightLine(Quadtree tree) {
        return new MyLineHelper(tree).getLeftRightLine();
    }

    public static MyLine getBottomTopLine(Quadtree tree) {
        return new MyLineHelper(tree).getBottomTopLine();
    }

    public static class Builder {

        private Quadtree tree;

        public Builder withQuadtree(Quadtree tree) {
            this.tree = tree;
            return this;
        }

        public MyLineHelper build() {
            return new MyLineHelper(tree);
        }
    }
}
