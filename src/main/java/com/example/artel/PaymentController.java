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

// class: Payment
// variable: payment, payments
// table: artel.payment
// resouses: String, Integer
// <Payment, String>
// <Payment, Integer>
// <Payment, Long>
// <Payment>

public class PaymentController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Payment, String> dateCol;

    @FXML
    private TextField dateInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Payment, Integer> amountCol;

    @FXML
    private TextField amountInput;

    @FXML
    private TableColumn<Payment, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Payment, Long> memberCol;

    @FXML
    private TextField memberInput;

    @FXML
    private TableView<Payment> table;


    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Payment, Long>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<Payment, String>("date"));
        dateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        amountCol.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("amount"));
        amountCol.setCellFactory(TextFieldTableCell.<Payment, Integer>forTableColumn(new IntegerStringConverter()));
        memberCol.setCellValueFactory(new PropertyValueFactory<Payment, Long>("memberId"));
        memberCol.setCellFactory(TextFieldTableCell.<Payment, Long>forTableColumn(new LongStringConverter()));
        updateTable();
        table.setEditable(true);

        dateCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Payment payment = ((Payment) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                payment.setDate(event.getNewValue());
                update(payment);
            }
        });
        memberCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Payment payment = ((Payment) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                payment.setMemberId(event.getNewValue());
                update(payment);
            }
        });
        amountCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Payment payment = ((Payment) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                payment.setAmount(event.getNewValue());
                update(payment);
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
            String amountValue = amountInput.getText();
            String dateValue = dateInput.getText();
            try {
                insert(Integer.valueOf(memberValue), Integer.valueOf(amountValue), dateValue);
                memberInput.setText("");
                amountInput.setText("");
                dateInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Payment> get() {
        ObservableList<Payment> shifts1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.payment ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d date:%s\n", rs.getLong("id"),
                        rs.getString("datum"));
                shifts1.add(new Payment(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getString("datum"),
                        rs.getInt("amount")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return shifts1;
    }

    private void insert(int memberValue, int amountValue, String dateValue) throws SQLException, ParseException {
        java.sql.Date date = Universal.dateParser(dateValue);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.payment (member_id, amount, datum) VALUES ( ?, ? ,?)");
        stmt.setLong(1, memberValue);
        stmt.setInt(2, amountValue);
        stmt.setDate(3, date);

        stmt.execute();
        updateTable();
    }

    public void update(Payment payment){
        try {
            java.sql.Date date = Universal.dateParser(payment.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.payment SET member_id = ?, amount = ?, datum = ? WHERE id = ?");
            stmt.setLong(1, payment.getMemberId());
            stmt.setInt(2, payment.getAmount());
            stmt.setDate(3, date);
            stmt.setLong(4, payment.getId());
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
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.payment WHERE id = ?");
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



