package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.app.interfaces.QuadtreeDrawer;
import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.Group;

import java.util.Arrays;
import java.util.Objects;

public class SimpleQuadtreeDrawer implements QuadtreeDrawer {

    final MyLineHelper  lineHelper = new MyLineHelper();

    public void setHeight(double height) {
        this.height = height;
    }

    double height;

    @Override
    public void draw(Quadtree tree, Group group) {
        if (!tree.isLeaf()) {
            AppConfig config = AppConfig.DEFAULT_CONFIG;
            MyLine bottomTopLine = lineHelper.getTopBottomLine(tree, config.getHeight());

            group.getChildren().add(bottomTopLine);

            MyLine leftRightLine = lineHelper.getLeftRightLine(tree, config.getHeight());

            group.getChildren().add(leftRightLine);
            Arrays.stream(tree.getQuadrants()).filter(q -> !q.isLeaf()).forEach(q -> draw(q, group));
        }
    }

    @Override
    public void erase(Quadtree quadtree) {

    }
}
