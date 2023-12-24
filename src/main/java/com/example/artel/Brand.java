package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Brand {
    SimpleLongProperty id;
    SimpleIntegerProperty capitalization, net;
    SimpleStringProperty name;

    public Brand(Long i, String n, int c, int ni) {
        id = new SimpleLongProperty(i);
        name = new SimpleStringProperty(n);
        capitalization = new SimpleIntegerProperty(c);
        net = new SimpleIntegerProperty(ni);
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

    public int getCapitalization() {
        return capitalization.get();
    }

    public SimpleIntegerProperty capitalizationProperty() {
        return capitalization;
    }

    public void setCapitalization(int capitalization) {
        this.capitalization.set(capitalization);
    }

    public int getNet() {
        return net.get();
    }

    public SimpleIntegerProperty netProperty() {
        return net;
    }

    public void setNet(int net) {
        this.net.set(net);
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
}
