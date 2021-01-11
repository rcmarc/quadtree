package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.app.interfaces.NodeAnimator;
import com.github.rcmarc.quadtree.core.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    final double width = AppConfig.DEFAULT_CONFIG.getWidth();
    final double height = AppConfig.DEFAULT_CONFIG.getHeight();
    Duration duration = AppConfig.DEFAULT_CONFIG.getAnimationDuration();

    NodeAnimator nodeAnimator = AppConfig.DEFAULT_CONFIG.getNodeAnimator();
    MouseEventCircleMapper mapper = new MouseEventCircleMapper();

    public void start(Stage stage) {

        Group group = new Group();

        Scene scene = new Scene(group, width, height);
        stage.setTitle("Quadtree Demo");
        stage.setScene(scene);
        stage.show();

        scene.setOnMouseClicked(event -> {
            Point2DCircle circle = mapper.map(event);
            group.getChildren().add(circle);
            nodeAnimator.animate(circle,height, width, duration);
        });

    }

    Data<Void> emptyData(Point2D point) {
        return new Data<>(null, point);
    }

    public static void main(String[] args) {
        launch(args);
    }
}