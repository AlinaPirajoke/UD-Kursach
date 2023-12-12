module com.example.artel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.example.artel to javafx.fxml;
    exports com.example.artel;
}