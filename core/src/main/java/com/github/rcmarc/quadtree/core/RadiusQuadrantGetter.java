package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RadiusQuadrantGetter implements QuadrantGetter {

    private final QuadtreeBoundaryHelper boundaryHelper;

    public RadiusQuadrantGetter() {
        this.boundaryHelper = new QuadtreeBoundaryHelper();
    }

    @Override
    public List<Quadtree> getQuadrants(Quadtree tree, Point2D point) {
        if (!boundaryHelper.isPointRadiusOverlayingQuadtree(tree, point)) {
            return Collections.emptyList();
        }

        return Arrays.stream(tree.getQuadrants())
                .filter(q -> boundaryHelper.isPointRadiusOverlayingQuadtree(q, point))
                .collect(Collectors.toList());
    }
}
