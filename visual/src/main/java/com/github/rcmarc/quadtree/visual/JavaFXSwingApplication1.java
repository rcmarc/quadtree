/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.github.rcmarc.quadtree.visual;

import java.io.IOException;
import java.util.Random;
import java.util.Arrays;
import java.util.LinkedList;

import com.github.rcmarc.quadtree.core.*;
import javafx.application.Application;
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
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

// Arregla esto que a mi no me dejo importarlo del proyecto original
// Esto lo tengo yo para probar que funciona bien.


public class JavaFXSwingApplication1 extends Application{

    final private Canvas canvas;
    final private double w, h;
    final private double real_rate = 100;
    // Point2D of us (this is for test)
    final private LinkedList<Point2D> ptos;
    // import quadtree
    final private Quadtree qtree;
    // Point selected
    Point2D select;
    
    public JavaFXSwingApplication1() {
        super();
        w = h = 550;
        // Paint plane
        canvas = new Canvas(w, h);
        ptos = new LinkedList<>();
        qtree = new NoDepthLimitQuadtree(new Point2D(w, h));
    }
    
    @Override
    public void start(Stage stage) throws IOException {        
        Separator sep = new Separator(Orientation.VERTICAL);   
        Separator sep2 = new Separator(Orientation.HORIZONTAL);
        Separator sep3 = new Separator(Orientation.HORIZONTAL);
        // Input point
        TextField x_pto = new TextField();
        TextField y_pto = new TextField();
        x_pto.setPromptText("x axis");
        x_pto.setMaxWidth(70);
        y_pto.setPromptText("y axis");
        y_pto.setMaxWidth(70);
        
//        Canvas y_axis = new Canvas(30, h);
//        y_axis.getGraphicsContext2D().strokeLine(30, 30, 30, h);
//        y_axis.getGraphicsContext2D().strokeText("100", 1, 40);
//        y_axis.getGraphicsContext2D().strokeText("0", 10, h);
//        
//        Canvas x_axis = new Canvas(w, 30);
//        x_axis.getGraphicsContext2D().strokeLine(30, 0, w-30, 0);
//        x_axis.getGraphicsContext2D().strokeText("100", w-50, 15);
//        
//        HBox hb_can = new HBox(y_axis, canvas);
//        VBox vb_can = new VBox(hb_can, x_axis);
        
        // Button
        Button btn_del = new Button("Delete Point");
        btn_del.setAlignment(Pos.CENTER);
        btn_del.setOnMouseClicked((MouseEvent event) -> {
            // Function to delete points in quadtree
            if(select != null) {
                qtree.delete(select);
                ptos.remove(select);
                select = null;
                drawQuadtree(canvas.getGraphicsContext2D());
            } else {
                if("".equals(x_pto.getText()) || "".equals(y_pto.getText()))
                    new Alert(Alert.AlertType.WARNING, "Some coordinate miss.")
                            .show();
                else {
                    Point2D pto = new Point2D(Double.parseDouble(x_pto.getText()), Double.parseDouble(y_pto.getText()));
                    try {
                        qtree.delete(pto);
                        ptos.remove(pto);
                        drawQuadtree(canvas.getGraphicsContext2D());
                        x_pto.setText("");
                        y_pto.setText("");
                    } catch(PointNotExistsException e) {
                        Alert a = new Alert(Alert.AlertType.ERROR, "Point ("+x_pto.getText()+";"+y_pto.getText()+") doesn't exist");
                        a.show();
                    } catch(NullPointerException e) {
                        // Ignore null execption
                    }
                }
            }
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
            select = null;
            drawQuadtree(canvas.getGraphicsContext2D());
        });
        
        StackPane root = createRoot();
        // Containers
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(100, 20, 0, 5));
        HBox hBox = new HBox(10);
        // hBox.setPadding(new Insets(10, 0, 10, 10));
        HBox hBtn = new HBox(10);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        //Parent root = loader.load();
        hBtn.getChildren().addAll(x_pto, y_pto);
        vBox.getChildren().addAll(sep3, hBtn, btn_del, sep2, btn_rand);
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
    
    private StackPane createRoot() {
        StackPane s = new StackPane();
        s.setOnMouseClicked((MouseEvent event) -> {
            double x = event.getSceneX();
            double y = event.getSceneY();
            Point2D tmp = new Point2D(x, y);
            
            if(x > w || x < 0 || y > h || y < 0) return;
            
            int index = Arrays.binarySearch(ptos.toArray(), tmp);
            if(index != -1)
            {
                canvas.getGraphicsContext2D().setFill(Color.RED);
                canvas.getGraphicsContext2D()
                    .fillOval(x, y, 5, 5);
                canvas.getGraphicsContext2D().setFill(Color.BLACK);
                select = tmp;
            } else
            { 
                canvas.getGraphicsContext2D()
                    .fillOval(x, y, 5, 5);
                Point2D p = new Point2D(x, y);
                System.out.println(x+" "+y);
                ptos.push(p);
                qtree.insert(new Data(0, p));
                select = null;
                drawQuadtree(canvas.getGraphicsContext2D());
            }
        });
        return s;
    }
}

