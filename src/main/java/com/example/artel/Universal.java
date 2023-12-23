package com.example.artel;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Universal {

    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // Формат даты

    public static void TextWindow(String text){
        // Создание метки с текстом
        Label label = new Label(text);
        label.setAlignment(Pos.BASELINE_LEFT);

// Добавление метки на панель
        StackPane root = new StackPane();
        root.getChildren().add(label);

// Создание новой сцены с панелью в качестве корневого узла
        Scene scene = new Scene(root, 300, 200);

// Установка сцены для окна
        Stage stage = new Stage();
        stage.setScene(scene);

// Отображение окна
        stage.show();
    }

    public static Date dateParser(String string) throws ParseException {
        java.util.Date parsedDate = format.parse(string);
        return new Date(parsedDate.getTime());
    }

//    public static void moveToExit(Button exitButton){
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
}
