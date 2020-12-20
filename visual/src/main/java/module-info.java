module quadtree.visual {
    requires javafx.fxml;
    requires javafx.controls;

    exports com.github.rcmarc.quadtree.visual to javafx.graphics;
    opens com.github.rcmarc.quadtree.visual to javafx.fxml;
}