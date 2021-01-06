package com.github.rcmarc.quadtree.visual;

import com.github.rcmarc.quadtree.core.Point2D;
import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.canvas.GraphicsContext;

public class GraphicsContextEliminator {


    static void deletePoint(GraphicsContext context, Quadtree tree, Point2D point) {
        context.clearRect(0,0,context.getCanvas().getWidth(),context.getCanvas().getHeight());
        tree.delete(point);
        QuadtreeDrawer.drawQuadtree(context,tree);
    }

}
