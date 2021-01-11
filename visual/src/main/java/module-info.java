module quadtree.visual {
    requires javafx.fxml;
    requires javafx.controls;
    requires quadtree.core;

    exports com.github.rcmarc.quadtree.visual to javafx.graphics, quadtree.app;
    opens com.github.rcmarc.quadtree.visual to javafx.fxml;
}