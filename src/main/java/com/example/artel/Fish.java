package com.example.artel;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Fish {
    public SimpleLongProperty id;
    public SimpleStringProperty name;

    public Fish(Long i, String n) {
        id = new SimpleLongProperty(i);
        name = new SimpleStringProperty(n);
    }

    public String getName() {
        return name.get();
    }

    public Long getId() {
        return id.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setId(Long id) {
        this.id.set(id);
    }
}
