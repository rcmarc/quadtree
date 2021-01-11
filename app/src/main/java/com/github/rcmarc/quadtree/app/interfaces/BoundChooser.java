package com.github.rcmarc.quadtree.app.interfaces;

import com.github.rcmarc.quadtree.app.Bound;

public interface BoundChooser {

    Bound getNextBound(Bound except);

    Bound getNextBound();
}
