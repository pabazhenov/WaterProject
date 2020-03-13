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
public class Organisation {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty comment;
    private final IntegerProperty price;
    boolean isdefault;
    
    public Organisation() {
        id = new SimpleIntegerProperty();
        price = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        comment = new SimpleStringProperty();
        isdefault = false;
    
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public int getPrice() {
        return price.get();
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public boolean isIsdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    public int getId(){
        return this.id.get();
    }
    
    @Override
    public String toString() {
        return this.name.get();
    }
    
}
