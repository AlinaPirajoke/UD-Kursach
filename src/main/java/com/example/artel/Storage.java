package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Storage {
    SimpleLongProperty prodId;
    SimpleIntegerProperty quantity;
    SimpleStringProperty date;

    public Storage(Long p, String d, int q) {
        prodId = new SimpleLongProperty(p);
        date = new SimpleStringProperty(d);
        quantity = new SimpleIntegerProperty(q);
    }

    public long getProdId() {
        return prodId.get();
    }

    public SimpleLongProperty prodIdProperty() {
        return prodId;
    }

    public void setProdId(long prodId) {
        this.prodId.set(prodId);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
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
