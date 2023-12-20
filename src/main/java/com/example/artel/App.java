package com.example.artel;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class App {
    // We can create a method to create and return a DataSource for our Postgres DB
    static DataSource createDataSource() {
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url =
                "jdbc:postgresql://localhost:5432/artel?user=postgres&password=postgres";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}