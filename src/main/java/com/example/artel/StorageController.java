package com.example.artel;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;

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
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import javax.sql.DataSource;

import static com.example.artel.App.createDataSource;

// class: Storage
// variable: storage, storages
// table: artel.storage
// resouses: String, Integer
// <Storage, String>
// <Storage, Integer>
// <Storage, Long>
// <Storage>

public class StorageController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Storage, String> dateCol;

    @FXML
    private TextField dateInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Storage, Integer> quanCol;

    @FXML
    private TextField quanInput;

    @FXML
    private TableColumn<Storage, Long> idCol;

    @FXML
    private TextField prodInput;

    @FXML
    private Button insert;

    @FXML
    private TableView<Storage> table;


    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Storage, Long>("prodId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Storage, String>("date"));
        quanCol.setCellValueFactory(new PropertyValueFactory<Storage, Integer>("quantity"));
        updateTable();

//        dateCol.setOnEditCommit(event -> {
//            if (event.getNewValue() != null){
//                Storage storage = ((Storage) event.getTableView().getItems()
//                        .get(event.getTablePosition().getRow()));
//                storage.setDate(event.getNewValue());
//                update(storage);
//            }
//        });
//        idCol.setOnEditCommit(event -> {
//            if (event.getNewValue() != null){
//                Storage storage = ((Storage) event.getTableView().getItems()
//                        .get(event.getTablePosition().getRow()));
//                storage.setProdId(event.getNewValue());
//                update(storage);
//            }
//        });
//        quanCol.setOnEditCommit(event -> {
//            if (event.getNewValue() != null){
//                Storage storage = ((Storage) event.getTableView().getItems()
//                        .get(event.getTablePosition().getRow()));
//                storage.setQuantity(event.getNewValue());
//                update(storage);
//            }
//        });

        Finder.setOnAction(event -> {
            find();
        });

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        delete.setOnAction(event -> {
            Storage store = table.getItems().get(table.getSelectionModel().getSelectedIndex());
            delete(store);
        });

        insert.setOnAction(event -> {
            String idValue = prodInput.getText();
            String quanValue = quanInput.getText();
            String dateValue = dateInput.getText();
            try {
                insert(Integer.valueOf(idValue), Integer.valueOf(quanValue), dateValue);
                prodInput.setText("");
                quanInput.setText("");
                dateInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Storage> get() {
        ObservableList<Storage> shifts1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.storage");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d quantity:%s\n", rs.getLong("product_id"),
                        rs.getString("quantity"));
                shifts1.add(new Storage(
                        rs.getLong("product_id"),
                        rs.getString("shelf_life"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return shifts1;
    }

    private void insert(int productValue, int quantityValue, String dateValue) throws SQLException, ParseException {
        java.sql.Date date = Universal.dateParser(dateValue);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.storage (product_id, shelf_life, quantity) VALUES ( ?, ? ,?)");
        stmt.setLong(1, productValue);
        stmt.setDate(2, date);
        stmt.setInt(3, quantityValue);


        stmt.execute();
        updateTable();
    }

//    public void update(Storage storage){
//        try {
//            java.sql.Date date = Universal.dateParser(storage.getDate());
//            DataSource dataSource = createDataSource();
//            Connection conn = dataSource.getConnection();
//            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.storage SET member_id = ?, amount = ?, datum = ? WHERE id = ?");
//            stmt.setLong(1, storage.getMemberId());
//            stmt.setInt(2, storage.getAmount());
//            stmt.setDate(3, date);
//            stmt.setLong(4, storage.getId());
//            stmt.execute();
//            updateTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Universal.TextWindow("Ошибка выполнения запроса!");
//        }
//    }

    private void delete(Storage storage){
        try {
            java.sql.Date date = Universal.dateParser(storage.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.storage WHERE product_id = ? AND shelf_life = ? AND quantity = ?");
            stmt.setLong(1, storage.getProdId());
            stmt.setDate(2, date);
            stmt.setInt(3, storage.getQuantity());
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
    void find(){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("QUERY");
            ResultSet rs = stmt.executeQuery();
            String text = "LEGEND\n\n";
            while (rs.next()) {
                System.out.printf("name:%s name:%s\n", rs.getString("name"),
                        rs.getInt("name"));
                text += rs.getString("name")
                        + " - " + rs.getInt("")
                        + "\n";
            }
            Universal.TextWindow(text);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
}



