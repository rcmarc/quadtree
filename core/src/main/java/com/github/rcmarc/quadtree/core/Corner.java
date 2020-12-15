package com.github.rcmarc.quadtree.core;

public enum Corner {
    TOP_LEFT(0),TOP_RIGHT(1),BOTTOM_LEFT(3), BOTTOM_RIGHT(2),MIDDLE(-1);
    int pos;
    Corner(int pos){
        this.pos = pos;
    }

}
