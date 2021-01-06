package com.github.rcmarc.quadtree.visual;

import com.github.rcmarc.quadtree.core.Data;
import com.github.rcmarc.quadtree.core.Point2D;
import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;

public class QuadtreeDrawer {

    private final GraphicsContext context;
    private final Quadtree tree;

    public QuadtreeDrawer(GraphicsContext context, Quadtree tree) {
        this.context = context;
        this.tree = tree;
    }

    void drawPoint(Point2D point) {
        drawPoint(context, point);
    }

    void eraseQuadtreeLines() {
        eraseQuadtreeLines(context, tree);
    }

    void drawQuadtree() {
        drawQuadtree(context, tree);
    }

    static void drawPoint(GraphicsContext context, Point2D point) {
        context.fillOval((int)point.getX(),
                (int)JavaFXHelper.getY(point.getY(), context.getCanvas().getHeight()),
                point.getRadius(),
                point.getRadius()
        );
    }

    static void eraseQuadtreeLines(GraphicsContext context, Quadtree quadtree) {
        Color current = (Color) context.getStroke();
        context.setStroke(Color.WHITESMOKE);
        drawQuadtree(context, quadtree);
        context.setStroke(current);
    }

    static void drawQuadtree(GraphicsContext context, Quadtree quadtree) {
        if (!quadtree.isLeaf()) {
            double height = context.getCanvas().getHeight();

            context.strokeLine(
                    (int)(quadtree.getOffset().getX() + quadtree.getDimension().getX() / 2),
                    (int)JavaFXHelper.getY(quadtree.getOffset().getY(), height),
                    (int)(quadtree.getOffset().getX() + quadtree.getDimension().getX() / 2),
                    (int)JavaFXHelper.getY(quadtree.getOffset().getY() + quadtree.getDimension().getY(), height)
            );

            context.strokeLine(
                    (int) quadtree.getOffset().getX(),
                    (int)JavaFXHelper.getY(quadtree.getOffset().getY() + quadtree.getDimension().getY() / 2, height),
                    (int)(quadtree.getOffset().getX() + quadtree.getDimension().getX()),
                    (int)JavaFXHelper.getY(quadtree.getOffset().getY() + quadtree.getDimension().getY() / 2, height));
        }

        Arrays.stream(quadtree.getQuadrants())
                .filter(Objects::nonNull)
                .forEach(tree -> drawQuadtree(context, tree));

        drawAllPoints(context, quadtree);
    }

    private static void drawAllPoints(GraphicsContext context, Quadtree tree) {
        if (tree.isLeaf()) {
            Arrays.stream(tree.getAllData())
                    .filter(Objects::nonNull)
                    .map(Data::getPoint)
                    .forEach(point -> QuadtreeDrawer.drawPoint(context, point));
        } else {
            Arrays.stream(tree.getQuadrants())
                    .forEach(quadtree -> drawAllPoints(context, quadtree));
        }

    }

    static class Builder {

        GraphicsContext context;
        Quadtree tree;

         Builder withContext(GraphicsContext context) {
            this.context = context;
            return this;
        }

        Builder withTree(Quadtree tree) {
             this.tree = tree;
             return this;
        }

        QuadtreeDrawer build() {
             return new QuadtreeDrawer(context, tree);
        }
    }
}
