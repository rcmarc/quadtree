package com.github.rcmarc.quadtree.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuadtreeBoundaryHelperTest {
    BoundaryHelper helper = new BoundaryHelper();
    Quadtree tree = new PointQuadtree(new Point2D(10,10));
    Quadtree tree_offset = new PointQuadtree(new Point2D(10,10) , new Point2D(5,5));

    @Test
    public void getMaxYTest() {
        assertEquals(10,helper.getMaxY(tree));
        assertEquals(15, helper.getMaxY(tree_offset));
    }

    @Test
    public void getMaxXTest() {
        assertEquals(10,helper.getMaxX(tree));
        assertEquals(15, helper.getMaxX(tree_offset));
    }


    @Test
    public void getMinXTest() {
        assertEquals(0,helper.getMinX(tree));
        assertEquals(5, helper.getMinX(tree_offset));
    }

    @Test
    public void getMinYTest() {
        assertEquals(0,helper.getMinY(tree));
        assertEquals(5, helper.getMinY(tree_offset));
    }

    @Test
    public void isPointOutsideQuadtreeTest() {
        Point2D negativePoint = new Point2D(-10,10);
        Point2D point = new Point2D(10,10);

        assertTrue(helper.isPointOutsideQuadtree(tree,negativePoint));
        assertTrue(helper.isPointOutsideQuadtree(tree_offset,negativePoint));
        assertFalse(helper.isPointOutsideQuadtree(tree,point));
        assertFalse(helper.isPointOutsideQuadtree(tree_offset,point));
    }

    @Test
    public void isPointBetweenVerticalEdgesTest(){
        Point2D point = new Point2D(25, 3);
        assertTrue(helper.isPointBetweenVerticalEdges(tree, point));
        assertFalse(helper.isPointBetweenVerticalEdges(tree_offset, point));
    }

    @Test
    public void isPointBetweenHorizontalEdgesTest(){
        Point2D point = new Point2D(3, 3);
        assertTrue(helper.isPointBetweenHorizontalEdges(tree, point));
        assertFalse(helper.isPointBetweenHorizontalEdges(tree_offset, point));
    }

    @Test
    public void isPointRadiusOverlayingQuadtreeTest(){
        Point2D point = new Point2D(6, 18, 4);
        assertTrue(helper.isPointOverlayingQuadtree(tree_offset, point));
        assertFalse(helper.isPointOverlayingQuadtree(tree, point));

        point = new Point2D(6, -5, 6);
        assertFalse(helper.isPointOverlayingQuadtree(tree_offset, point));
        assertTrue(helper.isPointOverlayingQuadtree(tree, point));

        point = new Point2D(18, 6, 4);
        assertTrue(helper.isPointOverlayingQuadtree(tree_offset, point));
        assertFalse(helper.isPointOverlayingQuadtree(tree, point));

        point = new Point2D(-5, 6, 6);
        assertFalse(helper.isPointOverlayingQuadtree(tree_offset, point));
        assertTrue(helper.isPointOverlayingQuadtree(tree, point));

        point = new Point2D(16, 16, 1);
        assertTrue(helper.isPointOverlayingQuadtree(tree_offset, point));
    }

}
