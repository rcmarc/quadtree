package com.github.rcmarc.quadtree.core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CornerToQuadtreeMapper {

    public Quadtree mapToQuadtree(Quadtree parent, Corner corner ) {
        return parent.getQuadrants()[corner.getPosition()];
    }

    public List<Quadtree> getQuadrants(Quadtree tree, Corner... corners) {
        return Arrays.stream(corners)
                .map(c -> mapToQuadtree(tree, c))
                .collect(Collectors.toList());
    }

}
