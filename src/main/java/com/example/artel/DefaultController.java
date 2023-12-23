//import com.example.artel.Universal;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.stage.Stage;
//import javafx.util.converter.IntegerStringConverter;
//import javafx.util.converter.LongStringConverter;
//
//import javax.sql.DataSource;
//
//import static com.example.artel.App.createDataSource;
//
//// class: XCLASS
//// variable: xclass, xclasses
//// table: artel.XTABLE
//// resouses: String, Integer
//// <XCLASS, String>
//// <XCLASS, Integer>
//// <XCLASS, Long>
//// <XCLASS>
//
//public class DefaultController {
//
//    @FXML
//    private ResourceBundle resources;
//
//    @FXML
//    private URL location;
//
//    @FXML
//    private Button Finder;
//
//    @FXML
//    private Button delete;
//
//    @FXML
//    private Button exitButton;
//
//    @FXML
//    private TableColumn<XCLASS, ?> idCol;
//
//    @FXML
//    private Button insert;
//
//    @FXML
//    private TableColumn<XCLASS, ?> nameCol;
//
//    @FXML
//    private TextField nameInput;
//
//    @FXML
//    private TableView<?> table;
//
//    @FXML
//    void initialize() {
//        idCol.setCellValueFactory(new PropertyValueFactory<XCLASS, Long>("id"));
//        nameCol.setCellValueFactory(new PropertyValueFactory<XCLASS, String>("name1"));
//        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        dolyaCol.setCellValueFactory(new PropertyValueFactory<XCLASS, Integer>("dolya"));
//        dolyaCol.setCellFactory(TextFieldTableCell.<XCLASS, Integer>forTableColumn(new IntegerStringConverter()));
//        passportCol.setCellValueFactory(new PropertyValueFactory<XCLASS, Long>("passport"));
//        passportCol.setCellFactory(TextFieldTableCell.<XCLASS, Long>forTableColumn(new LongStringConverter()));
//        updateTable();
//        table.setEditable(true);
//
//        .setOnEditCommit(event -> {
//            if (event.getNewValue() != null){
//                XCLASS xclass = ((XCLASS) event.getTableView().getItems()
//                        .get(event.getTablePosition().getRow()));
//                xclass.set(event.getNewValue());
//                update(xclass);
//            }
//        });
//
//        Finder.setOnAction(event -> {
//            find();
//        });
//
//        exitButton.setOnAction(event -> {
//            moveToExit();
//        });
//
//        delete.setOnAction(event -> {
//            long id = table.getItems().get(table.getSelectionModel().getSelectedIndex()).getId();
//            delete(id);
//        });
//
//        insert.setOnAction(event -> {
//            String Value = Input.getText();
//            String Value = Input.getText();
//            try {
//                insert(, Integer.valueOf());
//                Input.setText("");
//                Input.setText("");
//                updateTable();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Universal.TextWindow("Ошибка выполнения запроса!");
//            }
//        });
//    }
//
//    // Получение значений из бд
//    ObservableList<XCLASS> get() {
//        ObservableList<XCLASS> xclasses1 = FXCollections.observableArrayList();
//        try {
//            DataSource dataSource = createDataSource();
//            Connection conn = dataSource.getConnection();
//            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM artel.XTABLE ORDER BY id ASC");
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                System.out.printf("id:%d name:%s\n", rs.getLong("id"),
//                        rs.getString("name"));
//                xclasses1.add(new XCLASS(
//                        rs.getLong("id"),
//                        rs.getString("name"),
//                        rs.getInt("share"),
//                ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Universal.TextWindow("Ошибка выполнения запроса!");
//        }
//        return xclasses1;
//    }
//    // Запись значений в бд
//    private void insert(String Value, int Value, int Value) throws SQLException, ParseException {
//        //  java.sql.Date date = Universal.dateParser(Value);
//        DataSource dataSource = createDataSource();
//        Connection conn = dataSource.getConnection();
//        PreparedStatement stmt = conn.prepareStatement("INSERT INTO artel.XTABLE (share, passport, name, surname, patronymic) VALUES (?, ?, ? ,? ,?)");
//        stmt.setInt(1, );
//        stmt.setLong(2, );
//        stmt.setString(3, );
//
//        stmt.execute();
//        updateTable();
//    }
//
//    // Изменение записей в бд
//    public void update(XCLASS xclass){
//        try {
//            // java.sql.Date date = Universal.dateParser(xclass.getDate());
//            DataSource dataSource = createDataSource();
//            Connection conn = dataSource.getConnection();
//            PreparedStatement stmt = conn.prepareStatement("UPDATE artel.XTABLE SET share = ?, passport = ?, name = ?, surname = ?, patronymic = ? WHERE id = ?");
//            stmt.setInt(1, xclass.get());
//            stmt.setString(3, xclass.get());
//            stmt.setLong(n, xclass.getId());
//            stmt.execute();
//            updateTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Universal.TextWindow("Ошибка выполнения запроса!");
//        }
//    }
//
//    // Удаление записей из бд
//    private void delete(long id){
//        try {
//            DataSource dataSource = createDataSource();
//            Connection conn = dataSource.getConnection();
//            PreparedStatement stmt = conn.prepareStatement("DELETE FROM artel.XTABLE WHERE id = ?");
//            stmt.setLong(1, id);
//            stmt.execute();
//            updateTable();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Universal.TextWindow("Ошибка выполнения запроса!");
//        }
//    }
//
//    // Обновление таблицы
//    void updateTable(){
//        table.setItems(get());
//    }
//
//    // Выход
//    private void moveToExit() {
//        exitButton.getScene().getWindow().hide();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("hello-view.fxml"));
//
//        try {
//            loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Parent root = loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.showAndWait();
//    }
//
//    // Особый запрос
//    void find(){
//        try {
//            DataSource dataSource = createDataSource();
//            Connection conn = dataSource.getConnection();
//            PreparedStatement stmt = conn.prepareStatement("QUERY");
//            ResultSet rs = stmt.executeQuery();
//            String text = "LEGEND\n\n";
//            while (rs.next()) {
//                System.out.printf("name:%s name:%s\n", rs.getString("name"),
//                        rs.getInt("name"));
//                text += rs.getString("name")
//                        + " - " + rs.getInt("")
//                        + "\n";
//            }
//            Universal.TextWindow(text);
//        } catch (Exception e) {
//            System.err.println("Ошибка выполнения запроса!");
//            e.printStackTrace();
//        }
//    }
//}
//
