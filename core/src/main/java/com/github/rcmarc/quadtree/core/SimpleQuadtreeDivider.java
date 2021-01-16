package com.github.rcmarc.quadtree.core;

public class SimpleQuadtreeDivider implements QuadtreeDivider{
    @Override
    public void divide(Quadtree quadtree) {
        Quadtree[] subTrees = quadtree.getQuadrants();
        subTrees[0] = quadtree.genUpperLeftQuadrant();
        subTrees[1] = quadtree.genUpperRightQuadrant();
        subTrees[2] = quadtree.genLowerRightQuadrant();
        subTrees[3] = quadtree.genLowerLeftQuadrant();
    }
}
