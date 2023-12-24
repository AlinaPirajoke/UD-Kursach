package com.example.artel;

import com.example.artel.Universal;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import javax.sql.DataSource;

import static com.example.artel.App.createDataSource;

// class: Brand
// variable: brand, brands
// table: artel.brand
// resouses: String, Integer
// <Brand, String>
// <Brand, Integer>
// <Brand, Long>
// <Brand>

public class BrandController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Brand, Integer> capitalCol;

    @FXML
    private TextField capitalInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Brand, Long> idCol;

    @FXML
    private TableColumn<Brand, Integer> incomCol;

    @FXML
    private TextField incomeInput;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Brand, String> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private TableView<Brand> table;

    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Brand, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Brand, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        capitalCol.setCellValueFactory(new PropertyValueFactory<Brand, Integer>("capitalization"));
        capitalCol.setCellFactory(TextFieldTableCell.<Brand, Integer>forTableColumn(new IntegerStringConverter()));
        incomCol.setCellValueFactory(new PropertyValueFactory<Brand, Integer>("net"));
        incomCol.setCellFactory(TextFieldTableCell.<Brand, Integer>forTableColumn(new IntegerStringConverter()));
        updateTable();
        table.setEditable(true);

        nameCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Brand brand = ((Brand) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                brand.setName(event.getNewValue());
                update(brand);
            }
        });
        capitalCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Brand brand = ((Brand) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                brand.setCapitalization(event.getNewValue());
                update(brand);
            }
        });
        incomCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Brand brand = ((Brand) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                brand.setNet(event.getNewValue());
                update(brand);
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
            String nameValue = nameInput.getText();
            String capitalValue = capitalInput.getText();
            String incomValue = incomeInput.getText();
            try {
                insert(nameValue, Integer.valueOf(capitalValue), Integer.valueOf(incomValue));
                nameInput.setText("");
                capitalInput.setText("");
                incomeInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    // Получение значений из бд
    ObservableList<Brand> get() {
        ObservableList<Brand> brandes1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.brand ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d name:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                brandes1.add(new Brand(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("capitalization"),
                        rs.getInt("net_income")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return brandes1;
    }
    // Запись значений в бд
    private void insert(String nameValue, int capitalValue, int incomeValue) throws SQLException {
        //  java.sql.Date date = Universal.dateParser(Value);
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.brand (net_income, capitalization, name) VALUES (? ,? ,?)");
        stmt.setInt(1, incomeValue);
        stmt.setInt(2, capitalValue);
        stmt.setString(3, nameValue);

        stmt.execute();
        updateTable();
    }

    // Изменение записей в бд
    public void update(Brand brand){
        try {
            // java.sql.Date date = Universal.dateParser(brand.getDate());
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.brand SET net_income = ?,  capitalization = ?, name = ? WHERE id = ?");
            stmt.setInt(1, brand.getNet());
            stmt.setInt(2, brand.getCapitalization());
            stmt.setString(3, brand.getName());
            stmt.setLong(4, brand.getId());
            stmt.execute();
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
    }

    // Удаление записей из бд
    private void delete(long id){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.brand WHERE id = ?");
            stmt.setLong(1, id);
            stmt.execute();
            updateTable();
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
    }

    // Обновление таблицы
    void updateTable(){
        table.setItems(get());
    }

    // Выход
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

    // Особый запрос
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


