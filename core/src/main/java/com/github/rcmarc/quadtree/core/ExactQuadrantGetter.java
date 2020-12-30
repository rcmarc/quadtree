package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.rcmarc.quadtree.core.Corner.*;

public class ExactQuadrantGetter implements QuadrantGetter {

    private final CornerToQuadtreeMapper mapper;

    public ExactQuadrantGetter() {
        mapper = new CornerToQuadtreeMapper();
    }

    @Override
    public List<Quadtree> getQuadrants(Quadtree tree, Point2D point) {
        Point2D offset = tree.getOffset(), dimension = tree.getDimension();
        double max_x = offset.x + dimension.x, max_y = offset.y + dimension.y;

        if (point.x > max_x || point.y > max_y || point.x < offset.x || point.y < offset.y)
            return Collections.emptyList();

        Quadtree[] quadrants = tree.getQuadrants();

        double middle_x = offset.x + dimension.x / 2, middle_y = offset.y + dimension.y / 2;
        if (point.x == middle_x) {
            if (point.y == middle_y) return Arrays.asList(quadrants);
            return point.y > middle_y ? getQuadrants(tree,TOP_LEFT) : getQuadrants(tree,BOTTOM_RIGHT);
        } else if (point.y == middle_y) {
            return point.x > middle_x ? getQuadrants(tree,TOP_RIGHT) : getQuadrants(tree,BOTTOM_LEFT);
        } else if (point.x > middle_x) {
            return point.y > middle_y ? getQuadrants(tree,TOP_RIGHT) : getQuadrants(tree,BOTTOM_RIGHT);
        }
        return point.y > middle_y ? getQuadrants(tree,TOP_LEFT) : getQuadrants(tree,BOTTOM_LEFT);
    }

    private List<Quadtree> getQuadrants(Quadtree tree, Corner corner) {
        return mapper.getQuadrants(tree, corner);
    }
}
