package com.example.artel;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import javax.sql.DataSource;

import static com.example.artel.App.createDataSource;

// class: Ship
// variable: ship, ships
// table: artel.ship
// resouses: String, Integer
// <Ship, String>
// <Ship, Integer>
// <Ship, Long>
// <Ship>



public class ShipController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Ship, String> dateCol;

    @FXML
    private TextField dateInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Ship, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Ship, String> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private TableView<Ship> table;

    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Ship, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Ship, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dateCol.setCellValueFactory(new PropertyValueFactory<Ship, String>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        updateTable();
        table.setEditable(true);

        nameCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Ship ship = ((Ship) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                ship.setName(event.getNewValue());
                update(ship);
            }
        });
        dateCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Ship ship = ((Ship) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                ship.setDate(event.getNewValue());
                update(ship);
            }
        });

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        delete.setOnAction(event -> {
            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
            delete(id);
        });

        insert.setOnAction(event -> {
            String nameValue = nameInput.getText();
            String dateValue = dateInput.getText();
            try {
                insert(nameValue, dateValue);
                nameInput.setText("");
                dateInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Ship> get() {
        ObservableList<Ship> ships1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.ship ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d name:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                ships1.add(new Ship(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("launched")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return ships1;
    }

    private void insert(String nameValue, String dateValue) throws SQLException, ParseException {
        Date date =  Universal.dateParser(dateValue);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.ship (name, launched) VALUES (?, ?)");
        stmt.setString(1, nameValue);
        stmt.setDate(2, date);

        stmt.execute();
        updateTable();
    }

    public void update(Ship ship){
        try {
            Date date = Universal.dateParser(ship.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.ship SET name = ?, launched = ? WHERE id = ?");
            stmt.setString(1, ship.getName());
            stmt.setDate(2, date);
            stmt.setLong(3, ship.getId());
            stmt.execute();
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
    }

    private void delete(long id){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.ship WHERE id = ?");
            stmt.setLong(1, id);
            stmt.execute();
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
    }

    void updateTable(){
        table.setItems(get());
    }

    private void moveToExit() {
        exitButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    void findMostUsing(){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("QUERY");
            ResultSet rs = stmt.executeQuery();
            String text = "LEGEND\n\n";
            while (rs.next()) {
                System.out.printf("name:%s mane:%s\n", rs.getString("fname"),
                        rs.getString("pname"));
                text += rs.getString("fname")
                        + " - " + rs.getString("pname")
                        + "\n";
            }
            Universal.TextWindow(text);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
}


