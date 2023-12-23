package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Payment {
    SimpleLongProperty id, memberId;
    SimpleIntegerProperty amount;
    SimpleStringProperty date;

    public Payment(Long i, Long m, String d, int a) {
        id = new SimpleLongProperty(i);
        memberId = new SimpleLongProperty(m);
        date = new SimpleStringProperty(d);
        amount = new SimpleIntegerProperty(a);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public long getMemberId() {
        return memberId.get();
    }

    public SimpleLongProperty memberIdProperty() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId.set(memberId);
    }

    public int getAmount() {
        return amount.get();
    }

    public SimpleIntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
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
