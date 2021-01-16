package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.core.Quadtree;
import javafx.scene.Group;

public interface QuadtreeDrawer {

    void draw(Quadtree quadtree, Group group);

    void erase(Quadtree quadtree);

}
