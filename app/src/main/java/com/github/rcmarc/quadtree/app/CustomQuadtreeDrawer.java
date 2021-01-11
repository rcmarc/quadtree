package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomQuadtreeDrawer {

    private final List<MyLine> lineList = new LinkedList<>();
    private Quadtree tree;
    private Pane pane;
    private final MyLineHelper myLineHelper;

    public CustomQuadtreeDrawer(Quadtree tree, Pane pane) {
        this.tree = tree;
        this.pane = pane;
        myLineHelper = new MyLineHelper(tree);
    }

    public void startDrawing() {
        startDrawing(tree);
        startErasing(tree);
    }

    private void startDrawing(Quadtree tree) {
        tree.onSubdivide(trees -> {
//            Arrays.stream(trees).forEach(this::startDrawing);
            drawTree(tree);
        });
    }

    private void drawTree(Quadtree tree) {
        if (!tree.isLeaf()) {
            MyLine bottomTopLine = MyLineHelper.getBottomTopLine(tree);

            addLine(bottomTopLine);

            MyLine leftRightLine = MyLineHelper.getLeftRightLine(tree);

            addLine(leftRightLine);
            Arrays.stream(tree.getQuadrants()).forEach(this::drawTree);
        }
    }

    public void startErasing() {
        startErasing(tree);
    }

    private void startErasing(Quadtree tree) {
        tree.onReduced(this::removeLines);
    }

    private void removeLines(Quadtree tree) {
        removeLine(MyLineHelper.getBottomTopLine(tree));
        removeLine(MyLineHelper.getLeftRightLine(tree));
    }

    private void removeLine(MyLine line) {
        pane.getChildren().remove(line);
        lineList.remove(line);
    }

    private void addLine(MyLine line) {
        pane.getChildren().add(line);
        lineList.add(line);
    }

    static class Builder {

        Pane pane;
        Quadtree tree;

        public Builder withPane(Pane pane) {
            this.pane = pane;
            return this;
        }

        public Builder withQuadtree(Quadtree tree) {
            this.tree = tree;
            return this;
        }

        public CustomQuadtreeDrawer build() {
            return new CustomQuadtreeDrawer(tree, pane);
        }

    }

}
