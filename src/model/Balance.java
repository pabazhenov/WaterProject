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
public class Balance {
    private final IntegerProperty moneyAmount;
    private final StringProperty date;
    
    public Balance() {
        this.date = new SimpleStringProperty();
        this.moneyAmount = new SimpleIntegerProperty();
    }
    public int getMoneyAmount() {
        return moneyAmount.get();
    }
    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount.set(moneyAmount);
    }
    public IntegerProperty getMoneyAmountProperty() {
        return moneyAmount;
    }
    public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    public StringProperty getDateProperty() {
        return date;
    }
    
    
}
