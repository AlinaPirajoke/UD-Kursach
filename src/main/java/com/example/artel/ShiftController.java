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

// class: Shift
// variable: shift, shifts
// table: artel.shift
// resouses: String, Integer
// <Shift, String>
// <Shift, Integer>
// <Shift, Long>
// <Shift>

public class ShiftController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Shift, String> dateCol;

    @FXML
    private TextField dateInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Shift, Integer> hourCol;

    @FXML
    private TextField hourInput;

    @FXML
    private TableColumn<Shift, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Shift, Long> memberCol;

    @FXML
    private TextField memberInput;

    @FXML
    private TableColumn<Shift, Long> shipCol;

    @FXML
    private TextField shipInput;

    @FXML
    private TableView<Shift> table;


    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Shift, Long>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Shift, String>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        hourCol.setCellValueFactory(new PropertyValueFactory<Shift, Integer>("hours"));
        hourCol.setCellFactory(TextFieldTableCell.<Shift, Integer>forTableColumn(new IntegerStringConverter()));
        memberCol.setCellValueFactory(new PropertyValueFactory<Shift, Long>("memberId"));
        memberCol.setCellFactory(TextFieldTableCell.<Shift, Long>forTableColumn(new LongStringConverter()));
        shipCol.setCellValueFactory(new PropertyValueFactory<Shift, Long>("shipId"));
        shipCol.setCellFactory(TextFieldTableCell.<Shift, Long>forTableColumn(new LongStringConverter()));
        updateTable();
        table.setEditable(true);

        dateCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Shift shift = ((Shift) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                shift.setDate(event.getNewValue());
                update(shift);
            }
        });
        memberCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Shift shift = ((Shift) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                shift.setMemberId(event.getNewValue());
                update(shift);
            }
        });
        shipCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Shift shift = ((Shift) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                shift.setShipId(event.getNewValue());
                update(shift);
            }
        });
        hourCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Shift shift = ((Shift) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                shift.setHours(event.getNewValue());
                update(shift);
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
            String memberValue = memberInput.getText();
            String shipValue = shipInput.getText();
            String hourValue = hourInput.getText();
            String dateValue = dateInput.getText();
            try {
                insert(Integer.valueOf(memberValue), Integer.valueOf(shipValue), Integer.valueOf(hourValue), dateValue);
                memberInput.setText("");
                shipInput.setText("");
                hourInput.setText("");
                dateInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Shift> get() {
        ObservableList<Shift> shifts1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.shift ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d date:%s\n", rs.getLong("id"),
                        rs.getString("datum"));
                shifts1.add(new Shift(
                        rs.getLong("id"),
                        rs.getLong("ship_id"),
                        rs.getLong("member_id"),
                        rs.getString("datum"),
                        rs.getInt("hours_number")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return shifts1;
    }

    private void insert(int memberValue, int shipValue, int hourValue, String dateValue) throws SQLException, ParseException {
        java.sql.Date date = Universal.dateParser(dateValue);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.shift (member_id, ship_id, hours_number, datum) VALUES (?, ?, ? ,?)");
        stmt.setLong(1, memberValue);
        stmt.setLong(2, shipValue);
        stmt.setInt(3, hourValue);
        stmt.setDate(4, date);

        stmt.execute();
        updateTable();
    }

    public void update(Shift shift){
        try {
            java.sql.Date date = Universal.dateParser(shift.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.shift SET member_id = ?, ship_id = ?, hours_number = ?, datum = ? WHERE id = ?");
            stmt.setLong(1, shift.getMemberId());
            stmt.setLong(2, shift.getShipId());
            stmt.setInt(3, shift.getHours());
            stmt.setDate(4, date);
            stmt.setLong(5, shift.getId());
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
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.shift WHERE id = ?");
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


