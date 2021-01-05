package com.github.rcmarc.quadtree.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PointGetter {

    List<Data<?>> getAllData(Quadtree tree) {
        if (tree.isLeaf()) {
            return Arrays.stream(tree.getAllData())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        final List<Data<?>> dataList = new ArrayList<>();
        Arrays.stream(tree.getQuadrants()).forEach(quadtree -> dataList.addAll(getAllData(quadtree)));
        return dataList;
    }

    long getDataSize(Quadtree tree) {
        if (tree.isLeaf()) {
            return Arrays.stream(tree.getAllData()).filter(Objects::nonNull).count();
        }
        return Arrays.stream(tree.getQuadrants()).mapToLong(this::getDataSize).sum();
    }
}
