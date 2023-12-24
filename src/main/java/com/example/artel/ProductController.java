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

// class: Product
// variable: product, products
// table: artel.product
// resouses: String, Integer
// <Product, String>
// <Product, Integer>
// <Product, Long>
// <Product>

public class ProductController {

    @FXML
    private Button Finder;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private Button delete;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Product, Integer> costCol;

    @FXML
    private TextField costInput;

    @FXML
    private TableColumn<Product, Integer> soldCol;

    @FXML
    private TextField soldInput;

    @FXML
    private TableColumn<Product, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private TableColumn<Product, Long> brandCol;

    @FXML
    private TextField brandInput;

    @FXML
    private TableColumn<Product, Long> fishCol;

    @FXML
    private TextField fishInput;

    @FXML
    private TableView<Product> table;


    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Product, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        costCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("cost"));
        costCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        soldCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("sold"));
        soldCol.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
        brandCol.setCellValueFactory(new PropertyValueFactory<Product, Long>("brandId"));
        brandCol.setCellFactory(TextFieldTableCell.<Product, Long>forTableColumn(new LongStringConverter()));
        fishCol.setCellValueFactory(new PropertyValueFactory<Product, Long>("fishId"));
        fishCol.setCellFactory(TextFieldTableCell.<Product, Long>forTableColumn(new LongStringConverter()));
        updateTable();
        table.setEditable(true);

        nameCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Product product = ((Product) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                product.setName(event.getNewValue());
                update(product);
            }
        });
        brandCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Product product = ((Product) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                product.setBrandId(event.getNewValue());
                update(product);
            }
        });
        fishCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Product product = ((Product) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                product.setFishId(event.getNewValue());
                update(product);
            }
        });
        costCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Product product = ((Product) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                product.setCost(event.getNewValue());
                update(product);
            }
        });
        soldCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Product product = ((Product) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                product.setSold(event.getNewValue());
                update(product);
            }
        });

        Finder.setOnAction(event -> {
            //find();
        });

        exitButton.setOnAction(event -> {
            moveToExit();
        });

        delete.setOnAction(event -> {
            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
            delete(id);
        });

        insert.setOnAction(event -> {
            String brandValue = brandInput.getText();
            String fishValue = fishInput.getText();
            String costValue = costInput.getText();
            String soldValue = soldInput.getText();
            String nameValue = nameInput.getText();
            try {
                insert(Integer.valueOf(brandValue), Integer.valueOf(fishValue), Integer.valueOf(costValue), Integer.valueOf(soldValue), nameValue);
                brandInput.setText("");
                fishInput.setText("");
                costInput.setText("");
                soldInput.setText("");
                nameInput.setText("");
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка выполнения запроса!");
            }
        });
    }

    ObservableList<Product> get() {
        ObservableList<Product> products1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.product ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d name:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                products1.add(new Product(
                        rs.getLong("id"),
                        rs.getLong("brand_id"),
                        rs.getLong("fish_id"),
                        rs.getString("name"),
                        rs.getInt("cost"),
                        rs.getInt("sold")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Universal.TextWindow("Ошибка выполнения запроса!");
        }
        return products1;
    }

    private void insert(int brandValue, int fishValue, int costValue, int soldValue, String nameValue) throws SQLException, ParseException {
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.product (brand_id, fish_id, cost, sold, name) VALUES (?, ?, ? ,?, ?)");
        stmt.setLong(1, brandValue);
        stmt.setLong(2, fishValue);
        stmt.setInt(3, costValue);
        stmt.setInt(4, soldValue);
        stmt.setString(5, nameValue);

        stmt.execute();
        updateTable();
    }

    public void update(Product product){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.product SET brand_id = ?, fish_id = ?, cost = ?, sold = ?, name = ? WHERE id = ?");
            stmt.setLong(1, product.getBrandId());
            stmt.setLong(2, product.getFishId());
            stmt.setInt(3, product.getCost());
            stmt.setInt(4, product.getSold());
            stmt.setString(5, product.getName());
            stmt.setLong(6, product.getId());
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
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.product WHERE id = ?");
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


