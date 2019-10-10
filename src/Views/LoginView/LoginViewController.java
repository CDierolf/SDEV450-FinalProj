/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.LoginView;

import Classes.APIs.TicketMaster.TicketMasterAPI;
import Classes.APIs.TicketMaster.TicketMasterEvent;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable {
  
    @FXML
    private Button loginButton; 
    @FXML
    private Button exitButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
        TicketMasterAPI tma = new TicketMasterAPI();
        TicketMasterEvent tmeObject = new TicketMasterEvent();
        tmeObject = tma.findEvents("Butthole+Surfers", "3");
        
        List<Events> events = new ArrayList<>();
        events = tmeObject.getEmbeddedEvents().getEvents();
        
        for (Events e : events) {
            System.out.printf("Event name: %s\n", e.getName());
            System.out.printf("Event start date: %s\n", e.getDates().getEventStartData().getEventLocalDate());
            System.out.printf("Event start time: %s\n", e.getDates().getEventStartData().getEventLocalTime());
            System.out.printf("Event price: $%f\n", e.getPrice());
            System.out.printf("Event image url: %s\n", e.getImageUrl());
            
        }

//            tma.findEvents("Taylor Swift", "2");

    }    
    
}
