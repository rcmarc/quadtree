package com.github.rcmarc.quadtree.app;

import java.util.Random;

public class RandomContainer {

    final static Random random = new Random(System.currentTimeMillis());

    static double nextDouble(double lower, double upper) {
        return  (random.nextDouble() * (upper - lower)) + lower;
    }
}
