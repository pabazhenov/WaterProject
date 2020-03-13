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
public class Contribution {
    private final StringProperty userName;
    private final StringProperty date;
    private final IntegerProperty moneyAmount;

    public Contribution() {
        userName = new SimpleStringProperty();
        date = new SimpleStringProperty();
        moneyAmount = new SimpleIntegerProperty();
    
    }
    public String getUserName() {
        return userName.get();
    }

    public void setUserName(String username) {
        this.userName.set(username);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getMoneyAmount() {
        return moneyAmount.get();
    }

    public void setMoneyAmount(int MoneyAmount) {
        this.moneyAmount.set(MoneyAmount);
    }
    
}
