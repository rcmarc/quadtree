package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.interfaces.BoundChooser;

import java.util.Arrays;

public class SimpleBoundChooser implements BoundChooser {
    @Override
    public Bound getNextBound(Bound except) {
        Bound[] bounds = Arrays.stream(Bound.values())
                .filter(b -> !b.equals(except))
                .toArray(Bound[]::new);

        return bounds[RandomContainer.random.nextInt(bounds.length)];
    }

    @Override
    public Bound getNextBound() {
        return Bound.values()[RandomContainer.random.nextInt(4)];
    }
}
