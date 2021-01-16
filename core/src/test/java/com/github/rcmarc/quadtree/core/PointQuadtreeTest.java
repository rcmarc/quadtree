package com.github.rcmarc.quadtree.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointQuadtreeTest {

    @BeforeAll
    public static void setUp() {
        QuadtreeConfig.getConfig().setMaxPoints(1);
    }

    @Test
    public void testEquals(){
        PointQuadtree tree = new PointQuadtree(new Point2D(5,5));
        assertEquals(tree, new PointQuadtree(new Point2D(5,5)));
        assertEquals(tree, new PointQuadtree(new Point2D(5,5), new Point2D(0,0)));
        assertNotEquals(tree, new PointQuadtree(new Point2D(5,5), new Point2D(1,1)));
    }

//    @Test
//    public void testInsert(){
//        PointQuadtree tree = new PointQuadtree(new Point2D(10,10));
//        Point2D point_1 = new Point2D(4,6);
//        Point2D point_2 = new Point2D(2,7);
//        Point2D point_3 = new Point2D(1, 8);
//        tree.insert(new Data<>(5,point_1));
//        tree.insert(new Data<>(5,point_2));
//        tree.insert(new Data<>(5,point_3));
//
//        assertTrue(Arrays.stream(tree.quadrants).allMatch(Objects::nonNull));
//        assertEquals(new PointQuadtree(new Point2D(2.5,2.5), new Point2D(2.5,5)), tree.getQuadrant(point_1));
//        assertEquals(new PointQuadtree(new Point2D(2.5,2.5), new Point2D(0,5)), tree.getQuadrant(point_2));
//        assertEquals(new PointQuadtree(new Point2D(2.5,2.5), new Point2D(0,7.5)), tree.getQuadrant(point_3));
//    }

    @Test
    public void testDelete(){
        PointQuadtree tree = new PointQuadtree(new Point2D(10,10));
        Point2D point_1 = new Point2D(4,6);
        Point2D point_2 = new Point2D(2,7);
        Point2D point_3 = new Point2D(1, 8);
        tree.insert(point_1);
        tree.insert(point_2);
        tree.insert(point_3);
        tree.delete(point_1);
        assertTrue(tree.getQuadrants()[0].getQuadrants()[2].isLeaf());
        tree.delete(point_2);
        tree.delete(point_3);
        assertTrue(tree.isLeaf());
    }
}