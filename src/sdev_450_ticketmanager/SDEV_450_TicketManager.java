/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sdev_450_ticketmanager;

import Views.SeatMaps.Venue.Venue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf, Thomas Muck, Neil Hart, Stephen Graybeal
 * @Assignment Name: SDEV-450 Final Project - Ticket Master
 * @Date: Sep 24, 2019
 * @Description: Entry point for the application. Loads the LoginView
 */
// Test comment test commit
public class SDEV_450_TicketManager extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/LoginView/LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}