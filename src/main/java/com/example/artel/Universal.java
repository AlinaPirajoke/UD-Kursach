package com.example.artel;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Universal {
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
}
