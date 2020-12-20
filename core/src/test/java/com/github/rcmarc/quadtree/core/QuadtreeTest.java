package com.github.rcmarc.quadtree.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class QuadtreeTest {

    @Test
    public void testGetCorner()  {
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(10,10));
        Point2D point = new Point2D(5,6);
        assertEquals(Corner.TOP_LEFT, tree.getCorner(point));
        point = new Point2D(5,5);
        assertEquals(Corner.MIDDLE, tree.getCorner(point));
        point = new Point2D(0,0);
        assertEquals(Corner.BOTTOM_LEFT, tree.getCorner(point));
        point = new Point2D(10,10);
        assertEquals(Corner.TOP_RIGHT, tree.getCorner(point));
        point = new Point2D(7,5);
        assertEquals(Corner.TOP_RIGHT, tree.getCorner(point));
        point = new Point2D(5, 2);
        assertEquals(Corner.BOTTOM_RIGHT, tree.getCorner(point));
        point = new Point2D(3, 5);
        assertEquals(Corner.BOTTOM_LEFT, tree.getCorner(point));
        point = new Point2D(5, 7);
        assertEquals(Corner.TOP_LEFT, tree.getCorner(point));
        point = new Point2D(6,7);
        assertEquals(Corner.TOP_RIGHT, tree.getCorner(point));
        point = new Point2D(4,6);
        tree = new Quadtree<>(new Point2D(5,5),new Point2D(0,5));
        assertEquals(Corner.BOTTOM_RIGHT, tree.getCorner(point));
    }

    @Test
    public void testGetCornerWithOffset(){
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(10,10), new Point2D(5,5));
        Point2D point = new Point2D(5,6);
        assertEquals(Corner.BOTTOM_LEFT, tree.getCorner(point));
        point = new Point2D(5,5);
        assertEquals(Corner.BOTTOM_LEFT, tree.getCorner(point));
        point = new Point2D(10,10);
        assertEquals(Corner.MIDDLE, tree.getCorner(point));
        point = new Point2D(16,10);
        try {
             tree.getCorner(point);
            fail();
        }catch (OutsideQuadrantException ignored) {
            assertTrue(true);
        }
        point = new Point2D(7,5);
        assertEquals(Corner.BOTTOM_LEFT, tree.getCorner(point));
        point = new Point2D(5, 14);
        assertEquals(Corner.TOP_LEFT, tree.getCorner(point));
        point = new Point2D(14, 7.5);
        assertEquals(Corner.BOTTOM_RIGHT, tree.getCorner(point));
    }

    @Test
    public void testEquals(){
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(5,5));
        assertEquals(tree, new Quadtree<Integer>(new Point2D(5,5)));
        assertEquals(tree, new Quadtree<Integer>(new Point2D(5,5), new Point2D(0,0)));
        assertNotEquals(tree, new Quadtree<Integer>(new Point2D(5,5), new Point2D(1,1)));
    }

    @Test
    public void testSubdivide(){
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(10,10));
        tree.subdivide();
        assertEquals(tree.quadrants[0], new Quadtree<>(new Point2D(5,5), new Point2D(0,5)));
        assertEquals(tree.quadrants[1], new Quadtree<>(new Point2D(5,5), new Point2D(5,5)));
        assertEquals(tree.quadrants[3], new Quadtree<>(new Point2D(5,5)));
        assertEquals(tree.quadrants[2], new Quadtree<>(new Point2D(5,5), new Point2D(5,0)));

        Quadtree<Integer> offset_tree = new Quadtree<>(new Point2D(10,10), new Point2D(5,5));
        offset_tree.subdivide();
        assertEquals(offset_tree.quadrants[0], new Quadtree<>(new Point2D(5,5), new Point2D(5,10)));
        assertEquals(offset_tree.quadrants[1], new Quadtree<>(new Point2D(5,5), new Point2D(10,10)));
        assertEquals(offset_tree.quadrants[3], new Quadtree<>(new Point2D(5,5), new Point2D(5,5)));
        assertEquals(offset_tree.quadrants[2], new Quadtree<>(new Point2D(5,5), new Point2D(10,5)));
    }

    @Test
    public void testInsert(){
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(10,10));
        Point2D point_1 = new Point2D(4,6);
        Point2D point_2 = new Point2D(2,7);
        Point2D point_3 = new Point2D(1, 8);
        tree.insert(new Data<>(5,point_1));
        tree.insert(new Data<>(5,point_2));
        tree.insert(new Data<>(5,point_3));

        assertTrue(Arrays.stream(tree.quadrants).allMatch(Objects::nonNull));
        assertEquals(new Quadtree<Integer>(new Point2D(2.5,2.5), new Point2D(2.5,5)), tree.getQuadrant(point_1));
        assertEquals(new Quadtree<Integer>(new Point2D(2.5,2.5), new Point2D(0,5)), tree.getQuadrant(point_2));
        assertEquals(new Quadtree<Integer>(new Point2D(2.5,2.5), new Point2D(0,7.5)), tree.getQuadrant(point_3));
    }

    @Test
    public void testDelete(){
        Quadtree<Integer> tree = new Quadtree<>(new Point2D(10,10));
        Point2D point_1 = new Point2D(4,6);
        Point2D point_2 = new Point2D(2,7);
        Point2D point_3 = new Point2D(1, 8);
        tree.insert(new Data<>(5,point_1));
        tree.insert(new Data<>(5,point_2));
        tree.insert(new Data<>(5,point_3));
        tree.delete(point_1);
        assertTrue(tree.quadrants[0].quadrants[2].isEmpty());
        assertFalse(tree.isEmpty());
        tree.delete(point_2);
        assertTrue(tree.quadrants[0].quadrants[3].isEmpty());
        assertFalse(tree.isEmpty());
        tree.delete(point_3);
        assertTrue(tree.isEmpty());
    }
}