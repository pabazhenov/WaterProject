/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bazhenov_PA
 */
public class User {
    private final IntegerProperty id;
    private final StringProperty name;

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    public int getId() {
        return this.id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public User() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
    }
    @Override
    public String toString() {
        return this.name.get();
    }

}
