package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class RadiusQuadrantGetter implements QuadrantGetter{

    final BoundaryHelper helper = new BoundaryHelper();

    @Override
    public Collection<Quadtree> getQuadrants(Quadtree quadtree, Point2D point) {
        if (!helper.isPointOverlayingQuadtree(quadtree, point)) {
            return Collections.emptyList();
        }

        return Arrays.stream(quadtree.getQuadrants())
                .filter(Objects::nonNull)
                .filter(q -> helper.isPointOverlayingQuadtree(q, point))
                .collect(Collectors.toList());
    }
}
