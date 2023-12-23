package com.example.artel;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.StackPane;
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
    private Button fishUsingFinder;

    @FXML
    private TableColumn<Fish, Long> fishIdCol;

    @FXML
    private TableColumn<Fish, String> fishNameCol;

    @FXML
    private TableView<Fish> table;

    public FishController() throws SQLException {
    }

    @FXML
    void initialize() throws SQLException {
        fishIdCol.setCellValueFactory(new PropertyValueFactory<Fish, Long>("id"));
        fishNameCol.setCellValueFactory(new PropertyValueFactory<Fish, String>("name"));
        fishNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setItems(fishes);
        fishNameCol.setEditable(true);
        table.setEditable(true);

        fishNameCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Fish fish = ((Fish) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                fish.setName(event.getNewValue());
                updateFish(fish);
                table.setItems(getFishes());
            }
//            ((Fish) event.getTableView().getItems()
//                    .get(event.getTablePosition().getRow())).setName(value);
//            table.refresh();
        });

        delete.setOnAction(event -> {
            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
            delete(id);
            table.setItems(getFishes());
        });

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        insert.setOnAction(event -> {
            String value = fishNameInput.getText();
            fishNameInput.setText("");
            try {
                fishes = insertFish(value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            table.setItems(fishes);
        });

        fishUsingFinder.setOnAction(event -> {
            findUsingFish();
        });
    }

    ObservableList<Fish> getFishes() {
        ObservableList<Fish> fishes = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.fish ORDER BY id ASC");
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

    public void updateFish(Fish fish){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt0 = conn.prepareStatement("UPDATE artel.fish SET name = ? WHERE id = ?");
            stmt0.setString(1, fish.getName());
            stmt0.setLong(2, fish.getId());
            stmt0.execute();
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    ObservableList<Fish> insertFish(String value) throws SQLException {
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        ObservableList<Fish> fishes = FXCollections.observableArrayList();
        try {
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

    private void findUsingFish(){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select fi.name as fname, pr.name as pname from artel.fish as fi join artel.product as pr on pr.fish_id = fi.id;");
            ResultSet rs = stmt.executeQuery();
            String text = "РЫБА-ПРОДУКТ С ЕЁ СОДЕРЖАНИЕМ\n\n";
            while (rs.next()) {
                System.out.printf("name:%s mane:%s\n", rs.getString("fname"),
                        rs.getString("pname"));
                text += rs.getString("fname") + " - " + rs.getString("pname") + "\n";
            }
            Universal.TextWindow(text);
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    private void delete(long id){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.fish WHERE id = ?");
            stmt.setLong(1, id);
            stmt.execute();
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
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
