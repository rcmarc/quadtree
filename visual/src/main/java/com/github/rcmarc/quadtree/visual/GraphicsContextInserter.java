package com.github.rcmarc.quadtree.visual;

import com.github.rcmarc.quadtree.core.Point2D;
import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.canvas.GraphicsContext;

public class GraphicsContextInserter {

    private final GraphicsContext context;
    private final Quadtree tree;

    GraphicsContextInserter(GraphicsContext context, Quadtree tree) {
        this.context = context;
        this.tree = tree;
    }

    boolean insertPoint(Point2D point) {
        return insertPoint(this, point);
    }

    boolean insertPoint(double x, double y) {
        return insertPoint(this, x, y);
    }

    static boolean insertPoint(GraphicsContextInserter dataInserter, Point2D point) {
        if (dataInserter.tree.insert(point)) {
            QuadtreeDrawer.drawPoint(dataInserter.context, point);
            QuadtreeDrawer.drawQuadtree(dataInserter.context, dataInserter.tree);
            return true;
        }
        return false;
    }

    static boolean insertPoint(GraphicsContextInserter dataInserter, double x, double y) {
        return insertPoint(dataInserter, new Point2D(x, y));
    }

    static class GraphicsContextInserterBuilder {

        Quadtree tree;
        GraphicsContext context;

        GraphicsContextInserter build() {
            if (tree == null || context == null) {
                throw new IllegalStateException();
            }
            return new GraphicsContextInserter(
                    context,
                    tree
            );
        }

        GraphicsContextInserterBuilder withQuadtree(Quadtree tree) {
            this.tree = tree;
            return this;
        }

        GraphicsContextInserterBuilder withContext(GraphicsContext context) {
            this.context = context;
            return this;
        }
    }

}
