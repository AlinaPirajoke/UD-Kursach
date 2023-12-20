package com.example.artel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
       fish_btn.setOnAction(event -> {
          moveTo("fishTable.fxml");
       });
    }

    private void moveTo(String path){
        fish_btn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
