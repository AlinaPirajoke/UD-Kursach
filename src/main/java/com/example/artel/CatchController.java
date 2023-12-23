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

// class: Catch
// variable: catch1, catch1es
// table: artel.catch
// resouses: String, Integer
// <Catch, String>
// <Catch, Integer>
// <Catch, Long>
// <Catch>

public class CatchController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Catch, String> dateCol;

    @FXML
    private TextField dateInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Catch, Integer> weightCol;

    @FXML
    private TextField weightInput;

    @FXML
    private TableColumn<Catch, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Catch, Long> fishCol;

    @FXML
    private TextField fishInput;

    @FXML
    private TableColumn<Catch, Long> shipCol;

    @FXML
    private TextField shipInput;

    @FXML
    private TableView<Catch> table;


    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Catch, Long>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Catch, String>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        weightCol.setCellValueFactory(new PropertyValueFactory<Catch, Integer>("weight"));
        weightCol.setCellFactory(TextFieldTableCell.<Catch, Integer>forTableColumn(new IntegerStringConverter()));
        fishCol.setCellValueFactory(new PropertyValueFactory<Catch, Long>("fishId"));
        fishCol.setCellFactory(TextFieldTableCell.<Catch, Long>forTableColumn(new LongStringConverter()));
        shipCol.setCellValueFactory(new PropertyValueFactory<Catch, Long>("shipId"));
        shipCol.setCellFactory(TextFieldTableCell.<Catch, Long>forTableColumn(new LongStringConverter()));
        updateTable();
        table.setEditable(true);

        dateCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Catch catch1 = ((Catch) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                catch1.setDate(event.getNewValue());
                update(catch1);
            }
        });
        fishCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Catch catch1 = ((Catch) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                catch1.setFishId(event.getNewValue());
                update(catch1);
            }
        });
        shipCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Catch catch1 = ((Catch) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                catch1.setShipId(event.getNewValue());
                update(catch1);
            }
        });
        weightCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Catch catch1 = ((Catch) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                catch1.setWeight(event.getNewValue());
                update(catch1);
            }
        });

        Finder.setOnAction(event -> {
            find();
        });

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        delete.setOnAction(event -> {
            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
            delete(id);
        });

        insert.setOnAction(event -> {
            String fishValue = fishInput.getText();
            String shipValue = shipInput.getText();
            String weightValue = weightInput.getText();
            String dateValue = dateInput.getText();
            try {
                insert(Integer.valueOf(fishValue), Integer.valueOf(shipValue), Integer.valueOf(weightValue), dateValue);
                fishInput.setText("");
                shipInput.setText("");
                weightInput.setText("");
                dateInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Catch> get() {
        ObservableList<Catch> catch1es1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.catch ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d date:%s\n", rs.getLong("id"),
                        rs.getString("datum"));
                catch1es1.add(new Catch(
                        rs.getLong("id"),
                        rs.getLong("ship_id"),
                        rs.getLong("fish_id"),
                        rs.getString("datum"),
                        rs.getInt("weight")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return catch1es1;
    }

    private void insert(int fishValue, int shipValue, int weightValue, String dateValue) throws SQLException, ParseException {
        java.sql.Date date = Universal.dateParser(dateValue);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.catch (fish_id, ship_id, weight, datum) VALUES (?, ?, ? ,?)");
        stmt.setLong(1, fishValue);
        stmt.setLong(2, shipValue);
        stmt.setInt(3, weightValue);
        stmt.setDate(4, date);

        stmt.execute();
        updateTable();
    }

    public void update(Catch catch1){
        try {
            java.sql.Date date = Universal.dateParser(catch1.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.catch SET fish_id = ?, ship_id = ?, weight = ?, datum = ? WHERE id = ?");
            stmt.setLong(1, catch1.getFishId());
            stmt.setLong(2, catch1.getShipId());
            stmt.setInt(3, catch1.getWeight());
            stmt.setDate(4, date);
            stmt.setLong(5, catch1.getId());
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
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.catch WHERE id = ?");
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


