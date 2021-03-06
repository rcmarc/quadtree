package com.github.rcmarc.quadtree.app;

import com.github.rcmarc.quadtree.app.config.AppConfig;
import com.github.rcmarc.quadtree.app.interfaces.CircleFactory;
import com.github.rcmarc.quadtree.app.interfaces.NodeAnimator;
import com.github.rcmarc.quadtree.app.interfaces.QuadtreeDrawer;
import com.github.rcmarc.quadtree.core.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
    long count = 0;
    public void start(Stage stage) {
        HBox root = new HBox();
        quadtreeConfig.setMaxDepth(5);
        quadtreeConfig.setMaxPoints(3);
        appConfig.setRadius(3);

        quadtree = createQuadtree();

        Pane p = new Pane(group);
        p.setMinSize(appConfig.getWidth(), appConfig.getHeight());
        p.setMaxSize(appConfig.getWidth(), appConfig.getHeight());

        TextField fieldRadius = new TextField();
        fieldRadius.setPromptText("Radius");
        fieldRadius.setTooltip(new Tooltip("From %f to %f".formatted(appConfig.getMinRadius(), appConfig.getMaxRadius())));
        fieldRadius.textProperty().set(appConfig.getRadius() + "");
        fieldRadius.textProperty().addListener((observable, oldValue, newValue) -> {
            getDouble(newValue).ifPresentOrElse(value -> {
                if (value < appConfig.getMinRadius() || value > appConfig.getMaxRadius()) {
                    return;
                }
                appConfig.setRadius(value);
                particles.forEach(this::setRadius);
            }, () -> fieldRadius.setText(oldValue));
            fieldRadius.selectAll();
        });
        Label labelRadius = new Label("Radius: ");
        HBox hBoxRadius = new HBox(labelRadius, fieldRadius);
        hBoxRadius.setSpacing(5);

        TextField fieldWidth = new TextField();
        fieldWidth.setPromptText("Width");
        fieldWidth.setTooltip(new Tooltip("From %f to %f".formatted(appConfig.getMinWidth(), appConfig.getMaxWidth())));
        fieldWidth.textProperty().set(appConfig.getWidth() + "");
        fieldWidth.textProperty().addListener((observable, oldValue, newValue) -> {
            getDouble(newValue).ifPresentOrElse(value -> {
                if (value < appConfig.getMinWidth() || value > appConfig.getMaxWidth()) {
                    return;
                }
                appConfig.setWidth(value);
                quadtree = createQuadtree();
                p.setMinWidth(value);
                stage.setWidth(value + 300);
                stage.centerOnScreen();
                nodeAnimator.stop();
                nodeAnimator.animateAll(particles, appConfig.getHeight(), appConfig.getWidth(), appConfig.getAnimationDuration(), this::onParticleMoved);
            }, () -> fieldWidth.setText(oldValue));
            fieldWidth.selectAll();
        });
        Label labelWidth = new Label("Width: ");
        HBox hBoxWidth = new HBox(labelWidth, fieldWidth);
        hBoxWidth.setSpacing(5);

        TextField fieldHeight = new TextField();
        fieldHeight.setPromptText("Height");
        fieldHeight.setTooltip(new Tooltip("From %f to %f".formatted(appConfig.getMinHeight(), appConfig.getMaxHeight())));
        fieldHeight.textProperty().set(appConfig.getHeight() + "");
        fieldHeight.textProperty().addListener((observable, oldValue, newValue) -> {
            getDouble(newValue).ifPresentOrElse(value -> {
                if (value < appConfig.getMinHeight() || value > appConfig.getMaxHeight()) {
                    return;
                }
                appConfig.setHeight(value);
                quadtree = createQuadtree();
                p.setMinHeight(value);
                stage.setHeight(value + 100);
                stage.centerOnScreen();
                nodeAnimator.stop();
                nodeAnimator.animateAll(particles, appConfig.getHeight(), appConfig.getWidth(), appConfig.getAnimationDuration(), this::onParticleMoved);
            }, () -> {
                fieldHeight.setText(oldValue);
            });
            fieldHeight.selectAll();
        });
        Label labelHeight = new Label("Height: ");
        HBox hBoxHeight = new HBox(labelHeight, fieldHeight);
        hBoxHeight.setSpacing(5);

        TextField fieldMaxPoints = new TextField();
        fieldMaxPoints.setPromptText("Max Points");
        fieldMaxPoints.textProperty().set(quadtreeConfig.getMaxPoints() + "");
        fieldMaxPoints.textProperty().addListener((observable, oldValue, newValue) -> quadtreeConfig.setMaxPoints(getInt(newValue).orElse(quadtreeConfig.getMaxPoints())));
        Label labelMaxPoints = new Label("Max Points: ");
        HBox hBoxMaxPoints = new HBox(labelMaxPoints, fieldMaxPoints);
        hBoxMaxPoints.setSpacing(5);

        TextField fieldMaxDepth = new TextField();
        fieldMaxDepth.setPromptText("Max Depth");
        fieldMaxDepth.textProperty().set(quadtreeConfig.getMaxDepth() + "");
        fieldMaxDepth.textProperty().addListener((observable, oldValue, newValue) -> quadtreeConfig.setMaxDepth(getInt(newValue).orElse(quadtreeConfig.getMaxDepth())));
        Label labelMaxDepth = new Label("Max Depth: ");
        HBox hBoxMaxDepth = new HBox(labelMaxDepth, fieldMaxDepth);
        hBoxMaxDepth.setSpacing(5);

        onlyNumbers(fieldMaxDepth, fieldMaxPoints);

        Button btnAdd100Points = new Button("Add 100 points");
        btnAdd100Points.setAlignment(Pos.CENTER);

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

        VBox v1 = new VBox(hBoxRadius, hBoxWidth, hBoxHeight, hBoxMaxDepth, hBoxMaxPoints, rb1, rb2, btnAdd100Points, hBox);
        v1.setSpacing(10);
        v1.setPadding(new Insets(100, 20, 0, 20));
        root.getChildren().addAll(p, sep1, v1);

        btnAdd100Points.setOnAction(event -> addPoints(100));

        Scene scene = new Scene(root);
        stage.setTitle("Quadtree Demo");
        stage.setScene(scene);
        stage.show();


        addPoints(10);
        nodeAnimator.animateAll(particles, appConfig.getHeight(), appConfig.getWidth(), appConfig.getAnimationDuration(), this::onParticleMoved);
        p.setOnMouseClicked(this::addPoint);

    }

    private Optional<Integer> getInt(String number) {
        try {
            return Optional.of(Integer.parseInt(number));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    private Optional<Double> getDouble(String number) {
        try {
            return Optional.of(Double.parseDouble(number));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }


    private void onlyNumbers(TextField... fields) {
        for (var field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    field.setText(oldValue);
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
        count = 0;
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
        count = 0;
        for (var p : particles) {
            count++;
            if (quadtree.arePointsInRange(p.getPoint())) {
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

    private void addPoint(double x, double y) {
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

    private void setRadius(Point2DCircle circle) {
        if (!isOutside(circle.getPoint())) {
            circle.setRadius(appConfig.getRadius());
            circle.getPoint().setRadius(appConfig.getRadius());
        } else {
            double radius = appConfig.getRadius();
            double x = RandomContainer.nextDouble(radius * 2, appConfig.getWidth() - radius * 2);
            double y = RandomContainer.nextDouble(radius * 2, appConfig.getHeight() - radius * 2);
            circle.setRadius(radius);
            circle.setCenterX(x);
            circle.setCenterY(y);
            circle.getPoint().setX(x);
            circle.getPoint().setY(y);
            circle.getPoint().setRadius(radius);
        }
    }

    private boolean isOutside(Point2D point) {
        return point.getX() + appConfig.getRadius() >= appConfig.getWidth() ||
                point.getX() - appConfig.getRadius() <= 0 ||
                point.getY() + appConfig.getRadius() >= appConfig.getHeight() ||
                point.getY() - appConfig.getRadius() <= 0;

    }

    public static void main(String[] args) {
        launch(args);
    }
}