package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Catch {
    SimpleLongProperty id, shipId, fishId;
    SimpleIntegerProperty weight;
    SimpleStringProperty date;

    public Catch(Long i, Long s, Long f, String d,  int w) {
        id = new SimpleLongProperty(i);
        shipId = new SimpleLongProperty(s);
        fishId = new SimpleLongProperty(f);
        date = new SimpleStringProperty(d);
        weight = new SimpleIntegerProperty(w);
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

    public long getFishId() {
        return fishId.get();
    }

    public SimpleLongProperty fishIdProperty() {
        return fishId;
    }

    public void setFishId(long fishId) {
        this.fishId.set(fishId);
    }

    public int getWeight() {
        return weight.get();
    }

    public SimpleIntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
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
