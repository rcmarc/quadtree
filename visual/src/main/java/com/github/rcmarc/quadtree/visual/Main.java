/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.rcmarc.quadtree.visual;

import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

// Arregla esto que a mi no me dejo importarlo del proyecto original
// Esto lo tengo yo para probar que funciona bien.
import com.github.rcmarc.quadtree.core.Quadtree;
import com.github.rcmarc.quadtree.core.Data;
import com.github.rcmarc.quadtree.core.Point2D;

public class JavaFXSwingApplication1 extends Application{

    private Canvas canvas;
    private double w, h;
    // Point2D of us (this is for test)
    private LinkedList<Point2D> ptos;
    // import quadtree
    private Quadtree qtree;
    
    @Override
    public void start(Stage stage) throws IOException {        
        w = h = 550;
        // Paint plane
        canvas = new Canvas(w, h);
        ptos = new LinkedList<>();
        qtree = new Quadtree(new Point2D(w, h), new Point2D(0, 0), 4);
        
        Separator sep = new Separator(Orientation.VERTICAL);
        // Input point
        TextField x_pto = new TextField();
        TextField y_pto = new TextField();
        x_pto.setPromptText("x axis");
        x_pto.setMaxWidth(70);
        y_pto.setPromptText("y axis");
        y_pto.setMaxWidth(70);
        // Button
        Button btn_del = new Button("Delete Point");
        btn_del.setAlignment(Pos.CENTER);
        btn_del.setOnMouseClicked((MouseEvent event) -> {
            // Function to delete points in quadtree
            System.out.println("delete ==> x: "+x_pto.getText()+"; y: "+y_pto.getText());
        });
        
        Button btn_rand = new Button("Add random point");
        btn_rand.setOnMouseClicked((MouseEvent event) -> {
            Random rand = new Random();
            double x = w * rand.nextDouble();
            double y = h * rand.nextDouble();
            canvas.getGraphicsContext2D()
                    .fillOval(x, y, 5, 5);
            
            Point2D p = new Point2D(x, y);
            ptos.push(p);
            qtree.insert(new Data(0, p));
            drawQuadtree(canvas.getGraphicsContext2D());
        });

        StackPane root = new StackPane();
        root.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            canvas.getGraphicsContext2D()
                    .fillOval(x, y, 5, 5);
            
            Point2D p = new Point2D(x, y);
            ptos.push(p);
            qtree.insert(new Data(0, p));
            drawQuadtree(canvas.getGraphicsContext2D());
        });
        // Containers
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(100, 20, 0, 5));
        HBox hBox = new HBox(10);
        // hBox.setPadding(new Insets(10, 0, 10, 10));
        HBox hBtn = new HBox(10);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        //Parent root = loader.load();
        hBtn.getChildren().addAll(x_pto, y_pto);
        vBox.getChildren().addAll(hBtn, btn_del, btn_rand);
        hBox.getChildren().addAll(canvas, sep, vBox);
        root.getChildren().add(hBox);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // draw the quadtree
    private void drawQuadtree(GraphicsContext g) {
        g.clearRect(0, 0, w, h);
        
        // Build quadtree
        drawSubQuadtree(g, qtree);
        
        // Re-paint all points
        ptos.stream().forEach((p) -> {
            g.fillOval(p.getX(), p.getY(), 5, 5);
        });
    }
    
    private void drawSubQuadtree(GraphicsContext g, Quadtree q) {
        g.strokeLine(q.getOffset().getX()+q.getDimension().getX()/2, q.getOffset().getY(), 
                    q.getOffset().getX()+q.getDimension().getX()/2, q.getOffset().getY()+q.getDimension().getY());
        g.strokeLine(q.getOffset().getX(), q.getOffset().getY()+q.getDimension().getY()/2, 
                     q.getOffset().getX()+q.getDimension().getX(), q.getOffset().getY()+q.getDimension().getY()/2);
        
        if(q.getQuadrants()[0] != null) drawSubQuadtree(g, q.getQuadrants()[0]);
        if(q.getQuadrants()[1] != null) drawSubQuadtree(g, q.getQuadrants()[1]);
        if(q.getQuadrants()[2] != null) drawSubQuadtree(g, q.getQuadrants()[2]);
        if(q.getQuadrants()[3] != null) drawSubQuadtree(g, q.getQuadrants()[3]);
    }
}

