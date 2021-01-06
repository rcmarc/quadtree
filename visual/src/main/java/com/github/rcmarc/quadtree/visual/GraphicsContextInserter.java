package com.github.rcmarc.quadtree.visual;

import com.github.rcmarc.quadtree.core.Data;
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

    boolean insertData(Data<?> data) {
        return insertData(this, data);
    }

    <E> boolean insertData(double x, double y, E data) {
        return insertData(this, new Data<>(data, new Point2D(x, y)));
    }

    boolean insertData(double x, double y) {
        return insertData(this, x, y, null);
    }

    boolean insertData(Point2D point) {
        return insertData(this, new Data<>(null, point));
    }

    <E> boolean insertData(Point2D point, E data) {
        return insertData(this, new Data<>(data, point));
    }

    static boolean insertData(GraphicsContextInserter dataInserter, Data<?> data) {
        if (dataInserter.tree.insert(data)) {
            Point2D point = data.getPoint();
            QuadtreeDrawer.drawPoint(dataInserter.context, point);
            QuadtreeDrawer.drawQuadtree(dataInserter.context, dataInserter.tree);
            return true;
        }
        return false;
    }

    static <E> boolean insertData(GraphicsContextInserter dataInserter, double x, double y, E data) {
        return insertData(dataInserter, new Data<>(data, new Point2D(x, y)));
    }

    static boolean insertData(GraphicsContextInserter dataInserter, double x, double y) {
        return insertData(dataInserter, x, y, null);
    }

    static boolean insertData(GraphicsContextInserter dataInserter, Point2D point) {
        return insertData(dataInserter, new Data<>(null, point));
    }

    static <E> boolean insertData(GraphicsContextInserter dataInserter, Point2D point, E data) {
        return insertData(dataInserter, new Data<>(data, point));
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
