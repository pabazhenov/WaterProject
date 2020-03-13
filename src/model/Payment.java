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
public class Payment {
    private final StringProperty organisationName;
    private final IntegerProperty moneyAmount;
    private final StringProperty date;
    
    public Payment() {
        organisationName = new SimpleStringProperty();
        date = new SimpleStringProperty();
        moneyAmount = new SimpleIntegerProperty();
    }

    public String getOrganisationName() {
        return organisationName.get();
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName.set(organisationName);
    }
    
    public int getMoneyAmount() {
        return this.moneyAmount.get();
    }
    public void setMoneyAmount(int money) {
        this.moneyAmount.set(money);
    }
    public String getDate(){
        return this.date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
}
