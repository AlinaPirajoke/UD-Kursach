package com.example.artel;

import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FishController {

    ArrayList<Member> members;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<?, ?> dolyaCol;

    @FXML
    private TextField dolyaInput;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private Button insert;

    @FXML
    private Button memberTop5Finder;

    @FXML
    private TableColumn<?, ?> name2Col;

    @FXML
    private TextField name2Input;

    @FXML
    private TableColumn<?, ?> name3Col;

    @FXML
    private TextField name3Input;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private TableColumn<?, ?> passportCol;

    @FXML
    private TextField passportInput;

    @FXML
    private TableView<?> table;

    @FXML
    void initialize() {
        exitButton.setOnAction(event -> {
            moveToExit();
        });

        insert.setOnAction(event -> {
            String nameValue = nameInput.getText();
            String name2Value = name2Input.getText();
            String name3Value = name3Input.getText();
            String dolyaValue = dolyaInput.getText();
            String passportValue = passportInput.getText();
            try {
                members = insert(nameValue, name2Value, name3Value, dolyaValue, passportValue);
            } catch (SQLException e) {
                Universal.TextWindow("Ошибка!");
            }
            nameInput.setText("");
            name2Input.setText("");
            name3Input.setText("");
            dolyaInput.setText("");
            passportInput.setText("");
            table.setItems(members);
        });
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

    private ArrayList<Member> insert(String nameValue, String name2Value, String name3Value, int dolyaValue, int passportValue){

    }
}
