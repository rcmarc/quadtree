package com.github.rcmarc.quadtree.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RadiusQuadrantGetterTest {

    static RadiusQuadrantGetter getter = new RadiusQuadrantGetter();
    static Quadtree tree = new PointQuadtree(new Point2D(10, 10));
    static Quadtree tree_offset = new PointQuadtree(new Point2D(10, 10), new Point2D(5, 5));

    @BeforeAll
    static void setUp() {
        QuadtreeConfig.getConfig().setMaxPoints(1);
        insertAll(
                tree,
                new Point2D(7, 7),
                new Point2D(3, 3)
        );
        insertAll(
                tree_offset,
                new Point2D(12, 12),
                new Point2D(7, 7)
        );
    }

    @Test
    public void getQuadrants() {
        Point2D point = new Point2D(12, 12, 3);
        Quadtree expectedTree = new PointQuadtree(new Point2D(5, 5), new Point2D(5, 5));
        Collection<Quadtree> quadrantsOutput = getter.getQuadrants(tree, point);
        assertEquals(1, quadrantsOutput.size());
        assertEquals(expectedTree, quadrantsOutput.stream().findFirst().get());

        point = new Point2D(17,17, point.radius);
        expectedTree = new PointQuadtree(new Point2D(5,5), new Point2D(10,10));
        quadrantsOutput = getter.getQuadrants(tree_offset, point);
        assertEquals(1, quadrantsOutput.size());
        assertEquals(expectedTree, quadrantsOutput.stream().findFirst().get());

        point = new Point2D(5,5, point.radius);
        Point2D subDimension = new Point2D(5,5);
        List<Quadtree> treeList = List.of(
                new PointQuadtree(subDimension, new Point2D(0,5)),
                new PointQuadtree(subDimension, new Point2D(5,5)),
                new PointQuadtree(subDimension, new Point2D(5,0)),
                new PointQuadtree(subDimension, new Point2D(0,0))
        );
        quadrantsOutput = getter.getQuadrants(tree, point);
        assertEquals(treeList, quadrantsOutput);

        point = new Point2D(10,10, point.radius);
        subDimension = new Point2D(5,5);
        treeList = List.of(
                new PointQuadtree(subDimension, new Point2D(5,10)),
                new PointQuadtree(subDimension, new Point2D(10,10)),
                new PointQuadtree(subDimension, new Point2D(10,5)),
                new PointQuadtree(subDimension, new Point2D(5,5))
        );
        quadrantsOutput = getter.getQuadrants(tree_offset, point);
        assertEquals(treeList, quadrantsOutput);
    }

    static void insertAll(Quadtree tree, Point2D... points) {
        Arrays.stream(points).forEach(tree::insert);
    }
}
