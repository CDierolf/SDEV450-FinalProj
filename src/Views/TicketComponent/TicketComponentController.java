/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.DashboardView.DashboardViewController;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Christopher Dierolf
 * */
public class TicketComponentController implements Initializable {

    @FXML
    private Label eventLabel;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Label pricePerTicketLabel;
    @FXML
    private Button purchaseTicketsButton;
    @FXML
    private ImageView eventImageView;
    @FXML
    private Image eventImage;
    
    private Events event; // Event stored for UI interaction
    private DashboardViewController dvc; // To update Dashboard view
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    

    public void getEvent(Events event, DashboardViewController dvc) throws ExecutionException, InterruptedException {

        this.eventLabel.setText(event.getName());
        this.dateTimeLabel.setText(getEventDateTimeDetails(event));
        this.pricePerTicketLabel.setText("$" + Double.toString(event.getPrice()));
        try {
            getImage(event);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TicketComponentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Set event and dashboard variables
        this.event = event;
        this.dvc = dvc;
    }
    
    private String getEventDateTimeDetails(Events event) {
        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        String time = event.getEventDates().getEventStartData().getEventLocalTime();
        
        if (time == null) {
            time = "TBD";
        }
        
        return date + " " + time;
    }
    
    

    private void getImage(Events event) throws FileNotFoundException{
        
        //eventImage = new Image(event.getImageUrl());
        eventImageView.setImage(event.getEventImage());
    }
    
    // Event handler for "Puchase Tickets" button
    public void purchaseTickets() {
        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        String time = event.getEventDates().getEventStartData().getEventLocalTime();
        System.out.println(event.getName() + " " + date + time);
        dvc.loadVenue(event);
    }
}
