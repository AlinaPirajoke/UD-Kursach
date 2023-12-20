module com.example.artel {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires org.postgresql.jdbc;

    //opens com.example.artel to javafx.fxml;
    opens com.example.artel to javafx.base, javafx.graphics, javafx.fxml;
    exports com.example.artel;
}