package com.example.artel;

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

public class MemberController {

    ObservableList<Member> members;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button delete;

    @FXML
    private TableColumn<Member, Integer> dolyaCol;

    @FXML
    private TextField dolyaInput;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Member, Long> idCol;

    @FXML
    private Button insert;

    @FXML
    private Button memberTop5Finder;

    @FXML
    private TableColumn<Member, String> name2Col;

    @FXML
    private TextField name2Input;

    @FXML
    private TableColumn<Member, String> name3Col;

    @FXML
    private TextField name3Input;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private TableColumn<Member, Long> passportCol;

    @FXML
    private TextField passportInput;

    @FXML
    private TableView<Member> table;

    @FXML
    void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Member, Long>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Member, String>("name1"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        name2Col.setCellValueFactory(new PropertyValueFactory<Member, String>("name2"));
        name2Col.setCellFactory(TextFieldTableCell.forTableColumn());
        name3Col.setCellValueFactory(new PropertyValueFactory<Member, String>("name3"));
        name3Col.setCellFactory(TextFieldTableCell.forTableColumn());
        dolyaCol.setCellValueFactory(new PropertyValueFactory<Member, Integer>("dolya"));
        dolyaCol.setCellFactory(TextFieldTableCell.<Member, Integer>forTableColumn(new IntegerStringConverter()));
        passportCol.setCellValueFactory(new PropertyValueFactory<Member, Long>("passport"));
        passportCol.setCellFactory(TextFieldTableCell.<Member, Long>forTableColumn(new LongStringConverter()));
        table.setItems(getMembers());
        table.setEditable(true);

        nameCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Member member = ((Member) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                member.setName1(event.getNewValue());
                updateMember(member);
                table.setItems(getMembers());
            }
        });
        name2Col.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Member member = ((Member) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                member.setName2(event.getNewValue());
                updateMember(member);
                table.setItems(getMembers());
            }
        });
        name3Col.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Member member = ((Member) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                member.setName3(event.getNewValue());
                updateMember(member);
                table.setItems(getMembers());
            }
        });
        dolyaCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Member member = ((Member) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                member.setDolya(event.getNewValue());
                updateMember(member);
                table.setItems(getMembers());
            }
        });
        passportCol.setOnEditCommit(event -> {
            if (event.getNewValue() != null){
                Member member = ((Member) event.getTableView().getItems()
                        .get(event.getTablePosition().getRow()));
                member.setPassport(event.getNewValue());
                updateMember(member);
                table.setItems(getMembers());
            }
        });


        exitButton.setOnAction(event -> {
            moveToExit();
        });

        delete.setOnAction(event -> {
            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
            delete(id);
            table.setItems(getMembers());
        });

        insert.setOnAction(event -> {
            String nameValue = nameInput.getText();
            String name2Value = name2Input.getText();
            String name3Value = name3Input.getText();
            String dolyaValue = dolyaInput.getText();
            String passportValue = passportInput.getText();
            try {
                insert(nameValue, name2Value, name3Value, Integer.valueOf(dolyaValue), Integer.valueOf(passportValue));
                nameInput.setText("");
                name2Input.setText("");
                name3Input.setText("");
                dolyaInput.setText("");
                passportInput.setText("");
                table.setItems(getMembers());
            } catch (Exception e) {
                e.printStackTrace();
                Universal.TextWindow("Ошибка!");
            }
        });
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

    private void insert(String nameValue, String name2Value, String name3Value, int dolyaValue, int passportValue) throws SQLException {
        DataSource dataSource = createDataSource();
        Connection conn = dataSource.getConnection();
        PreparedStatement stmt0 = conn.prepareStatement("INSERT INTO artel.member (share, passport, name, surname, patronymic) VALUES (?, ?, ? ,? ,?)");
        stmt0.setInt(1, dolyaValue);
        stmt0.setLong(2, passportValue);
        stmt0.setString(3, nameValue);
        stmt0.setString(4, name2Value);
        stmt0.setString(5, name3Value);

        stmt0.execute();
    }

    ObservableList<Member> getMembers() {
        ObservableList<Member> members1 = FXCollections.observableArrayList();
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.member ORDER BY id ASC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.printf("id:%d name:%s\n", rs.getLong("id"),
                        rs.getString("name"));
                members1.add(new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("patronymic"),
                        rs.getInt("share"),
                        rs.getLong("passport")
                ));
            }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
        return members1;
    }

    public void updateMember(Member member){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt0 = conn.prepareStatement("UPDATE artel.member SET share = ?, passport = ?, name = ?, surname = ?, patronymic = ? WHERE id = ?");
            stmt0.setInt(1, member.getDolya());
            stmt0.setLong(2, member.getPassport());
            stmt0.setString(3, member.getName1());
            stmt0.setString(4, member.getName2());
            stmt0.setString(5, member.getName3());
            stmt0.setLong(6, member.getId());
            stmt0.execute();
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    private void delete(long id){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.member WHERE id = ?");
            stmt.setLong(1, id);
            stmt.execute();
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

    void findMostPowerful(){
        try {
            DataSource dataSource = createDataSource();
            Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement("select name, surname, share from artel.member ORDER BY share DESC LIMIT 5");
            ResultSet rs = stmt.executeQuery();
            String text = "5 САМЫХ ВЛИЯТЕЛЬНЫХ ЧЛЕНОВ:\nИМЯ - ФАМИЛИЯ - ВЛОЖЕНИЯ\n\n";
            while (rs.next()) {
                System.out.printf("name:%s share:%s\n", rs.getString("name"),
                        rs.getInt("share"));
                text += rs.getString("name")
                        + " - " + rs.getString("surname")
                        + " - " + rs.getInt("share")
                        + "\n";
            }
            Universal.TextWindow(text);
        } catch (Exception e) {
            System.err.println("Ошибка выполнения запроса!");
            e.printStackTrace();
        }
    }
}
