/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static model.DBConnector.connection;

/**
 *
 * @author Bazhenov_PA
 */
public class DataAccessor {
    
    public ObservableList<Balance> getAllBalance() throws SQLException{
        ObservableList<Balance> balanceList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "SELECT * FROM balance ORDER BY DATE desc LIMIT 50";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            Balance balance = new Balance();
            balance.setDate(rs.getDate("date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            balance.setMoneyAmount(rs.getInt("money_amount"));
            balanceList.add(balance);
        }
        rs.close();
        stmnt.close();   
        return balanceList;
    }
    public Balance getCurrentBalance() throws SQLException {
        Balance balance = new Balance();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "SELECT * FROM balance ORDER BY DATE desc LIMIT 1";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            balance.setDate(rs.getDate("date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            balance.setMoneyAmount(rs.getInt("money_amount"));
        }
        rs.close();
        stmnt.close();   
        return balance;
    }
    
    public ObservableList<Payment> getPayments() throws SQLException {
        ObservableList<Payment> paymentsList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "SELECT pays.money_amount,pays.date,org.name FROM payments pays,organisations org \n" +
                        "WHERE pays.organisations_id = org.id \n" +
                        "ORDER BY pays.date desc " +
                        "LIMIT 50";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while(rs.next()) {
            Payment payment = new Payment();
            payment.setDate(rs.getDate("date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            payment.setMoneyAmount(rs.getInt("money_amount"));
            payment.setOrganisationName(rs.getString("name"));
            paymentsList.add(payment);
        }
        rs.close();
        stmnt.close();
        return paymentsList;
    }
    public void makePayment(int moneyAmount) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "INSERT INTO payments SET organisations_id="+getDefaultOrganisation().getId()+", money_amount="+moneyAmount
                +", date=\""+todayDate+"\"";
        stmnt.execute(query);
        int newBalance = getCurrentBalance().getMoneyAmount()-moneyAmount;
        addNewBalance(newBalance);
        stmnt.close();
    }
    public ObservableList<Contribution> getContributions() throws SQLException {
        ObservableList<Contribution> contributionList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "SELECT contr.money_amount,contr.date,usr.name FROM contributions contr,users usr " +
                        "WHERE contr.users_id = usr.id " +
                        "ORDER BY contr.date desc " +
                        "LIMIT 50";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            Contribution contribution = new Contribution();
            contribution.setUserName(rs.getString("name"));
            contribution.setMoneyAmount(rs.getInt("money_amount"));
            contribution.setDate(rs.getDate("date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contributionList.add(contribution);
        }
        rs.close();
        stmnt.close();
        return contributionList;
    }
    
    public ObservableList<Contribution> getLastContributionsByUser() throws SQLException {
        ObservableList<Contribution> contributionList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "SELECT contr.date, usr.name " +
                        "FROM contributions contr, users usr " +
                        "WHERE contr.id IN( " +
                        "	SELECT MAX(c.id) FROM contributions c group BY c.users_id " +
                        ") AND contr.users_id = usr.id " +
                        "ORDER BY contr.date " +
                        "LIMIT 4";
        ResultSet  rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            Contribution contribution = new Contribution();
            contribution.setUserName(rs.getString("name"));
            contribution.setDate(rs.getDate("date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            contributionList.add(contribution);
        }
        rs.close();
        stmnt.close();
        return contributionList;
    }
    public ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "Select * From Users";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            userList.add(user);
        }
        rs.close();
        stmnt.close();
        return userList;
    }
    public void makeContribution(User user, int moneyAmount) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "INSERT INTO contributions SET users_id="+user.getId()+", money_amount="+moneyAmount
                +", date=\""+todayDate+"\"";
        stmnt.execute(query);
        int newBalance = getCurrentBalance().getMoneyAmount()+moneyAmount;
        addNewBalance(newBalance);
        stmnt.close();
    }
    public ObservableList<Organisation> getAllOrganisations() throws SQLException {
        ObservableList<Organisation> organisationList = FXCollections.observableArrayList();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "Select * From Organisations";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while (rs.next()) {
            Organisation organisation = new Organisation();
            organisation.setId(rs.getInt("id"));
            organisation.setName(rs.getString("name"));
            organisation.setComment(rs.getString("comment"));
            organisation.setPhone(rs.getString("phone_number"));
            organisation.setPrice(rs.getInt("price"));
            if (rs.getInt("is_default")==1) {
                organisation.setIsdefault(true);
            } else {
                organisation.setIsdefault(false);
            }
            organisationList.add(organisation);
        }
        rs.close();
        stmnt.close();
        return organisationList;
    }
    public Organisation getDefaultOrganisation() throws SQLException {
        Organisation organisation = new Organisation();
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "Select * from organisations where is_default=1";
        ResultSet rs;
        rs = stmnt.executeQuery(query);
        while(rs.next()) {
            organisation.setId(rs.getInt("id"));
            organisation.setIsdefault(true);
            organisation.setName(rs.getString("name"));
            organisation.setPhone(rs.getString("phone_number"));
            organisation.setPrice(rs.getInt("price"));
            organisation.setComment(rs.getString("comment"));
        }
        rs.close();
        stmnt.close();
        return organisation;
    }
    public void setDefaultOrganisation(Organisation organisation) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "Update Organisations SET is_default=0";
        stmnt.execute(query);
        query = "UPDATE Organisations SET is_default=1 WHERE id="+organisation.getId();
        stmnt.execute(query);
        stmnt.close();
    }
    public void addOrganisation(Organisation organisation) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "INSERT INTO Organisations SET name=\""+organisation.getName()+"\", "+
                        "phone_number=\""+organisation.getPhone()+"\", is_default=0,  "+
                        "comment=\""+organisation.getComment()+"\", price="+organisation.getPrice();
        stmnt.execute(query);
        stmnt.close();
    }
    public void addUser(User user) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "INSERT INTO Users SET name=\""+user.getName()+"\"";
        stmnt.execute(query);
        stmnt.close();
    }
    public void removeUser(User user) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "DELETE FROM Users where id="+user.getId()+"";
        stmnt.execute(query);
        stmnt.close();
    }
    public void removeOrganisation(Organisation organisation) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "DELETE FROM Organisations where id="+organisation.getId()+"";
        stmnt.execute(query);
        stmnt.close();
    
    }
    public void updateUser(User user) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "UPDATE Users SET name=\""+user.getName()+"\" WHERE id="+user.getId();
        stmnt.execute(query);
        stmnt.close();
    }
    public void updateOrganisation(Organisation organisation) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String query = "UPDATE Organisations SET name=\""+organisation.getName()+"\", "+
                        "phone_number=\""+organisation.getPhone()+"\", "+
                        "comment=\""+organisation.getComment()+"\", price="+organisation.getPrice()+
                        " where id="+organisation.getId();
        stmnt.execute(query);
        stmnt.close();
    
    }
    public void addNewBalance(int balance) throws SQLException {
        Statement stmnt;
        stmnt = connection.createStatement();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query = "INSERT INTO balance SET money_amount="+balance+", date=\""+todayDate+"\"";
        stmnt.execute(query);
        stmnt.close();
    }
}
