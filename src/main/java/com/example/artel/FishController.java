package com.example.artel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.sql.DataSource;

import static com.example.artel.App.createDataSource;

public class FishController {
    ObservableList<Fish> fishes = getFishes();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fishNameInput;

    @FXML
    private Button exitButton;

    @FXML
    private Button insert;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Fish, Long> fishIdCol;

    @FXML
    private TableColumn<Fish, String> fishNameCol;

    @FXML
    private TableView<Fish> table;

    @FXML
    void initialize() {

        fishIdCol.setCellValueFactory(new PropertyValueFactory<Fish, Long>("id"));
        fishNameCol.setCellValueFactory(new PropertyValueFactory<Fish, String>("name"));
        table.setItems(fishes);

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        insert.setOnAction(event -> {
            String value = fishNameInput.getText();
            fishNameInput.setText("");
            fishes = insertFish(value);
            table.setItems(fishes);

        });
    }

    ObservableList<Fish> getFishes() {
        ObservableList<Fish> fishes = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.fish");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d fish:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                fishes.add(new Fish( rs.getLong("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }

        return fishes;
    }

    ObservableList<Fish> insertFish(String value) {
        ObservableList<Fish> fishes = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt0 = conn.prepareStatement("INSERT INTO artel.fish (name) VALUES (?)");
            stmt0.setString(1, value);
            stmt0.execute();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.fish");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d fish:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                fishes.add(new Fish( rs.getLong("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }

        return fishes;
    }



    private void moveToExit(){
        exitButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));

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
