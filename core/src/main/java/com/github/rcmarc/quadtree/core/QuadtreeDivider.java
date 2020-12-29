package com.github.rcmarc.quadtree.core;

public class QuadtreeDivider {

    private static QuadtreeProvider provider;

    public QuadtreeDivider(QuadtreeProvider provider) {
        QuadtreeDivider.provider = provider;
    }

    public void setProvider(QuadtreeProvider provider) {
        QuadtreeDivider.provider = provider;
    }

    public void subdivide(Quadtree tree) {
        Point2D dimension = tree.getDimension();
        Point2D sub_dimension = dimension.divide(2), offset = tree.getOffset();
        Quadtree[] quadrants = tree.getQuadrants();

        quadrants[0] = provider.getInstance(
                sub_dimension,
                new Point2D(offset.x, offset.y + dimension.y / 2),
                tree.getMaxDataAllowed(),
                tree.getMaxDepth(),
                tree.allowLeaf()
        );
        quadrants[1] = provider.getInstance(
                sub_dimension,
                new Point2D(offset.x + dimension.x / 2, offset.y + dimension.y / 2),
                tree.getMaxDataAllowed(),
                tree.getMaxDepth(),
                tree.allowLeaf()
        );
        quadrants[2] = provider.getInstance(
                sub_dimension,
                new Point2D(offset.x + dimension.x / 2, offset.y),
                tree.getMaxDataAllowed(),
                tree.getMaxDepth(),
                tree.allowLeaf()
        );
        quadrants[3] = provider.getInstance(
                sub_dimension,
                offset,
                tree.getMaxDataAllowed(),
                tree.getMaxDepth(),
                tree.allowLeaf()
        );
    }

}
