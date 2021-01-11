module quadtree.app {
    requires javafx.controls;
    requires quadtree.core;
    requires quadtree.visual;
    exports com.github.rcmarc.quadtree.app to javafx.graphics;
    exports com.github.rcmarc.quadtree.app.config;
    exports com.github.rcmarc.quadtree.app.interfaces;
}