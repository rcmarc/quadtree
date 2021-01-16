package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.app.interfaces.CircleFactory;
import com.github.rcmarc.quadtree.app.interfaces.NodeAnimator;
import com.github.rcmarc.quadtree.app.interfaces.QuadtreeDrawer;
import com.github.rcmarc.quadtree.core.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    AppConfig appConfig = AppConfig.DEFAULT_CONFIG;
    QuadtreeConfig quadtreeConfig = QuadtreeConfig.getConfig();
    NodeAnimator nodeAnimator = appConfig.getNodeAnimator();

    List<Point2DCircle> particles = new LinkedList<>();
    CircleFactory circleFactory = appConfig.getCircleFactory();
    EuclideanDistanceGetter distanceGetter = new EuclideanDistanceGetter();
    QuadtreeDrawer drawer = new SimpleQuadtreeDrawer();
    Quadtree quadtree;
    Group group = new Group();
    SimpleDoubleProperty checksPerSecond = new SimpleDoubleProperty();

    public void start(Stage stage) {

        quadtreeConfig.setMaxDepth(5);
        quadtreeConfig.setMaxPoints(3);
        appConfig.setRadius(3);
        appConfig.setHeight(500);
        appConfig.setWidth(700);

        quadtree = createQuadtree();

        Pane p = new Pane(group);
        p.setMinSize(appConfig.getWidth(), appConfig.getHeight());
        p.setMaxSize(appConfig.getWidth(), appConfig.getHeight());

        TextField fieldMaxPoints = new TextField();
        fieldMaxPoints.setPromptText("Max Points");
        TextField fieldMaxDepth = new TextField();
        fieldMaxDepth.setPromptText("Max Depth");
        onlyNumbers(fieldMaxDepth, fieldMaxPoints);
        fieldMaxDepth.textProperty().addListener((observable, oldValue, newValue) -> quadtreeConfig.setMaxDepth(Integer.parseInt(newValue)));
        fieldMaxPoints.textProperty().addListener((observable, oldValue, newValue) -> quadtreeConfig.setMaxPoints(Integer.parseInt(newValue)));


        Button btnAdd100Points = new Button("Add 100 points");
        btnAdd100Points.setAlignment(Pos.CENTER);
        Separator sep = new Separator(Orientation.VERTICAL);

        HBox b1 = new HBox(fieldMaxPoints, sep, fieldMaxDepth);
        b1.setSpacing(5);

        Separator sep1 = new Separator(Orientation.VERTICAL);

        RadioButton rb1 = new RadioButton("Use Quadtree");
        RadioButton rb2 = new RadioButton("Don't use it");

        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            appConfig.setUseQuadtree(rb1.isSelected());
            if (!appConfig.isUsingQuadtree()) {
                quadtree = null;
                clearLines();
            }
        });
        rb1.setSelected(true);


        Label checksLabel = new Label("Checks per second:");
        Label checksPerSecondLabel = new Label();
        checksPerSecondLabel.textProperty().bind(checksPerSecond.asString());

        HBox hBox = new HBox(checksLabel, checksPerSecondLabel);
        hBox.setSpacing(5);

        VBox v1 = new VBox(b1, rb1, rb2, btnAdd100Points, hBox);
        v1.setSpacing(10);
        v1.setPadding(new Insets(100, 20, 0, 20));
        HBox root = new HBox(p,sep1, v1);

        btnAdd100Points.setOnAction(event -> addPoints(100));

        Scene scene = new Scene(root);
        stage.setTitle("Quadtree Demo");
        stage.setScene(scene);
        stage.show();


        addPoints(10);
        nodeAnimator.animateAll(particles, appConfig.getHeight(), appConfig.getWidth(), appConfig.getAnimationDuration(), this::onParticleMoved);
        p.setOnMouseClicked(this::addPoint);

    }

    private void onlyNumbers(TextField... fields) {
        for (var field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
    }

    private Quadtree createQuadtree() {
        Quadtree quadtree = new RegionQuadtree(new Point2D(appConfig.getWidth(), appConfig.getHeight()), 0);
        particles.forEach(p -> quadtree.insert(p.getPoint()));
        return quadtree;
    }

    private void clearLines() {
        group.getChildren().removeIf(node -> node instanceof MyLine);
    }

    private void initQuadtree() {
        quadtree = createQuadtree();
        clearLines();
        drawer.draw(quadtree, group);
    }

    private void withoutQuadtree() {
        long count = 0;
        for (var p : particles) {
            boolean match = false;
            for (var p1 : particles) {
                count++;
                if (!p.getPoint().equals(p1.getPoint()))
                    if (intersects(p.getPoint(), p1.getPoint())) {

                        match = true;
                        break;
                    }
            }
            if (match) {
                p.setFill(Color.BLACK);
            } else {
                p.setFill(p.getInitialColor());
            }
        }
        setChecksPerSecond(count);
    }

    private void withQuadtree() {
        initQuadtree();
        double count = 0;
        for(var p : particles) {
            boolean match = false;
            var others = quadtree.query(p.getPoint());
            for (var p1 : others) {
                count++;
                if (!p.getPoint().equals(p1))
                    if (intersects(p.getPoint(), p1)) {
                        match = true;
                        break;
                    }
            }
            if (match) {
                p.setFill(Color.BLACK);
            } else {
                p.setFill(p.getInitialColor());
            }
        }
        setChecksPerSecond(count);
    }

    private void setChecksPerSecond(double count) {
        checksPerSecond.set(count / appConfig.getAnimationDuration().toSeconds());
    }

    private void onParticleMoved() {
        if (appConfig.isUsingQuadtree()) {
            withQuadtree();
        } else {
            withoutQuadtree();
        }

    }

    private boolean intersects(Point2D p1, Point2D p2) {
        return distanceGetter.getDistance(p1, p2) <= AppConfig.DEFAULT_CONFIG.getRadius() * 2;
    }

    private void addPoint(MouseEvent event) {
        addPoint(event.getSceneX(), event.getSceneY());
    }

    private void addPoint(double x, double y){
        Point2DCircle circle = circleFactory.buildCircle(new Point2D(
                x, appConfig.getHeight() - y, appConfig.getRadius()
        ));
        group.getChildren().add(circle);
        particles.add(circle);
    }

    private void addPoints(int number) {
        for (int i = 0; i < number; i++) {
            addRandomPoint();
        }
    }

    private void addRandomPoint() {
        double radius = appConfig.getRadius();
        Point2DCircle circle = circleFactory.buildCircle(new Point2D(
                RandomContainer.nextDouble(radius * 2, appConfig.getWidth() - radius * 2),
                RandomContainer.nextDouble(radius * 2, appConfig.getHeight() - radius * 2),
                radius
        ));
        group.getChildren().add(circle);
        particles.add(circle);
    }

    public static void main(String[] args) {
        launch(args);
    }
}