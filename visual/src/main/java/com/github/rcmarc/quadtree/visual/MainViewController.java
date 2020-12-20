package com.github.rcmarc.quadtree.visual;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    public Button button;
    public Label label;

    public void handleButtonAction(ActionEvent actionEvent) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
