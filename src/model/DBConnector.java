/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 *
 * @author Bazhenov_PA
 */
public class DBConnector {
    public static Connection connection;
    
    public static void setConnection() throws SQLException {
        String db = "jdbc:mysql://127.0.0.1/water_project?serverTimezone=" + TimeZone.getDefault().getID();
        connection = DriverManager.getConnection(db,"root","Qq91133755");
    }
    public static boolean isConnected() {
        return connection!=null;
    }
    
    public static void setDisconnect() throws SQLException {
            connection.close();
    }
    
}
