package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    SimpleLongProperty id, brandId, fishId;
    SimpleIntegerProperty cost, sold;
    SimpleStringProperty name;

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public long getBrandId() {
        return brandId.get();
    }

    public SimpleLongProperty brandIdProperty() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId.set(brandId);
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

    public int getCost() {
        return cost.get();
    }

    public SimpleIntegerProperty costProperty() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost.set(cost);
    }

    public int getSold() {
        return sold.get();
    }

    public SimpleIntegerProperty soldProperty() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold.set(sold);
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

    public Product(Long i, Long b, Long f, String n, int c, int s) {
        id = new SimpleLongProperty(i);
        brandId = new SimpleLongProperty(b);
        fishId = new SimpleLongProperty(f);
        name = new SimpleStringProperty(n);
        cost = new SimpleIntegerProperty(c);
        sold = new SimpleIntegerProperty(s);
    }
}
