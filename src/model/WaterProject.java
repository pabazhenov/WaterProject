/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Bazhenov_PA
 */
public class WaterProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml") );
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("Учет воды");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/view/icon.png")));
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() {
        if (DBConnector.isConnected())
            try {
                DBConnector.setDisconnect();
        } catch (SQLException ex) {
            Logger.getLogger(WaterProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
