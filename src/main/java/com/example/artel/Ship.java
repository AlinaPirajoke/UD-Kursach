package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.ParseException;

public class Ship {
    SimpleLongProperty id;
    SimpleStringProperty name, date;

    public Ship(long i, String n, String d) {
        id = new SimpleLongProperty(i);
        name = new SimpleStringProperty(n);
        date = new SimpleStringProperty(d);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
