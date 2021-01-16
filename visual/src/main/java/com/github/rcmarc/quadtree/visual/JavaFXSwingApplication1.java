/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.rcmarc.quadtree.visual;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import com.github.rcmarc.quadtree.core.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

// Arregla esto que a mi no me dejo importarlo del proyecto original
// Esto lo tengo yo para probar que funciona bien.
import com.github.rcmarc.quadtree.core.PointQuadtree;
import com.github.rcmarc.quadtree.core.Point2D;

public class JavaFXSwingApplication1 extends Application{

    final static private double radius = 8;

    final private Canvas canvas;
    final private double w, h;
    // Point2D of us (this is for test)
    // import quadtree
    private Quadtree qtree;

    private PointChecker pointChecker;

    private Point2D select;

    private GraphicsContextInserter dataInserter;

    public JavaFXSwingApplication1() {
        super();
        w = h = 550;
        // Paint plane
        canvas = new Canvas(w, h);

        QuadtreeConfig config = QuadtreeConfig.getConfig();
        config.setMaxDepth(4);
        config.setMaxPoints(1);

        qtree = buildQuadtree();
        pointChecker = new SimplePointChecker();
        dataInserter = buildDataInserter();

    }

    private Quadtree buildQuadtree() {
        return new PointQuadtree(new Point2D(w, h), 0);
    }

    private GraphicsContextInserter buildDataInserter() {
        return new GraphicsContextInserter.GraphicsContextInserterBuilder()
                .withContext(canvas.getGraphicsContext2D())
                .withQuadtree(qtree)
                .build();
    }

    @Override
    public void start(Stage stage){
        Separator sep = new Separator(Orientation.VERTICAL);   
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        Separator sep3 = new Separator(Orientation.HORIZONTAL);

        // Button
        Button btn_del = new Button("Delete Point");
        btn_del.setAlignment(Pos.CENTER);
        btn_del.setOnMouseClicked((MouseEvent event) -> {
            // Function to delete points in quadtree
            getSelectedPoint().ifPresent(point -> GraphicsContextEliminator.deletePoint(canvas.getGraphicsContext2D(), qtree, point));

            select = null;
        });
        
        Button btn_rand = new Button("Add random point");
        btn_rand.setOnMouseClicked((MouseEvent event) -> {
            Random rand = new Random();
            double x = w * rand.nextDouble();
            double y = h * rand.nextDouble();
            dataInserter.insertPoint(getPoint(x, JavaFXHelper.getY(y, h)));
            select = null;
        });

        Button clear_btn = new Button("Clear");
        clear_btn.setOnAction(event -> {
            qtree = buildQuadtree();
            dataInserter = buildDataInserter();
            canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        });
        
        StackPane root = createRoot();
        // Containers
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(100, 20, 0, 5));
        HBox hBox = new HBox(10);

        vBox.getChildren().addAll(btn_del, sep2, btn_rand, sep3, clear_btn);
        hBox.getChildren().addAll(canvas, sep, vBox);
        root.getChildren().add(hBox);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


    
    private StackPane createRoot() {
        StackPane s = new StackPane();
        s.setOnMouseClicked((MouseEvent event) -> {
            GraphicsContext context = canvas.getGraphicsContext2D();

            double x = event.getSceneX();
            double y = event.getSceneY();
            Point2D tmp = getPoint(x, JavaFXHelper.getY(y, h));
            
            if(x > w || x < 0 || y > h || y < 0) return;

            getPointInQuadtree(qtree, tmp).ifPresentOrElse(p -> {

                getSelectedPoint().ifPresent(point -> QuadtreeDrawer.drawPoint(context, point));

                context.setFill(Color.RED);
                QuadtreeDrawer.drawPoint(context, p);
                context.setFill(Color.BLACK);
                select = p;
            },() -> {
                dataInserter.insertPoint(tmp);
                select = null;
            });
        });
        return s;
    }

    Optional<Point2D> getPointInQuadtree(Quadtree tree, Point2D point) {
        if (tree.isLeaf()) {
            return tree.getPoints().stream()
                    .filter(p -> isPointIn(point, p))
                    .findFirst();
        }

        return Arrays.stream(tree.getQuadrants())
                .map(q -> getPointInQuadtree(q, point).orElse(null))
                .filter(Objects::nonNull)
                .findFirst();
    }

    boolean isPointIn(Point2D exact, Point2D radius) {
        return exact.getX() <= radius.getX() + radius.getRadius() && exact.getX() >= radius.getX() - radius.getRadius() &&
                exact.getY() <= radius.getY() + radius.getRadius() && exact.getY() >= radius.getY() - radius.getRadius();
    }

    Optional<Point2D> getSelectedPoint() {
        return Optional.ofNullable(select);
    }

    static Point2D getPoint(double x, double y) {
        return new Point2D(x,y, radius);
    }

}

