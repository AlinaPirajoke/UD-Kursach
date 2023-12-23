package com.example.artel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {
    SimpleLongProperty id, passport;
    SimpleIntegerProperty dolya;
    SimpleStringProperty name1, name2, name3;

    public Member(Long i, String n1, String n2, String n3,  int d, long p) {
        id = new SimpleLongProperty(i);
        name1 = new SimpleStringProperty(n1);
        name2 = new SimpleStringProperty(n2);
        name3 = new SimpleStringProperty(n3);
        dolya = new SimpleIntegerProperty(d);
        passport = new SimpleLongProperty(p);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public int getDolya() {
        return dolya.get();
    }

    public SimpleIntegerProperty dolyaProperty() {
        return dolya;
    }

    public void setDolya(int dolya) {
        this.dolya.set(dolya);
    }

    public long getPassport() {
        return passport.get();
    }

    public SimpleLongProperty passportProperty() {
        return passport;
    }

    public void setPassport(long passport) {
        this.passport.set(passport);
    }

    public String getName1() {
        return name1.get();
    }

    public SimpleStringProperty name1Property() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1.set(name1);
    }

    public String getName2() {
        return name2.get();
    }

    public SimpleStringProperty name2Property() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2.set(name2);
    }

    public String getName3() {
        return name3.get();
    }

    public SimpleStringProperty name3Property() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3.set(name3);
    }
}
