package com.example.artel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button brand_btn;

    @FXML
    private Button catch_btn;

    @FXML
    private Button exit_btn;

    @FXML
    private Button fish_btn;

    @FXML
    private GridPane main_label;

    @FXML
    private Button members_btn;

    @FXML
    private Button payment_btn;

    @FXML
    private Button products_btn;

    @FXML
    private Button shift_btn;

    @FXML
    private Button ship_btn;

    @FXML
    private Button storage_btn;

    @FXML
    void initialize() {
        assert brand_btn != null : "fx:id=\"brand_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert catch_btn != null : "fx:id=\"catch_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert exit_btn != null : "fx:id=\"exit_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert fish_btn != null : "fx:id=\"fish_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert main_label != null : "fx:id=\"main_label\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert members_btn != null : "fx:id=\"members_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert payment_btn != null : "fx:id=\"payment_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert products_btn != null : "fx:id=\"products_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert shift_btn != null : "fx:id=\"shift_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert ship_btn != null : "fx:id=\"ship_btn\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert storage_btn != null : "fx:id=\"storage_btn\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
