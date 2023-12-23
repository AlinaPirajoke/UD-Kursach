package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Shift {
    SimpleLongProperty id, shipId, memberId;
    SimpleIntegerProperty hours;
    SimpleStringProperty date;

    public Shift(Long i, Long s, Long m, String d,  int h) {
        id = new SimpleLongProperty(i);
        shipId = new SimpleLongProperty(s);
        memberId = new SimpleLongProperty(m);
        date = new SimpleStringProperty(d);
        hours = new SimpleIntegerProperty(h);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public long getShipId() {
        return shipId.get();
    }

    public SimpleLongProperty shipIdProperty() {
        return shipId;
    }

    public void setShipId(long shipId) {
        this.shipId.set(shipId);
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

    public int getHours() {
        return hours.get();
    }

    public SimpleIntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
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
