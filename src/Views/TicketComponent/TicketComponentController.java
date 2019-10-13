/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO obtain event
        // pass into getEvent()
    }    
    
    public void getEvent(Events event) {
        this.eventLabel.setText(event.getName());
        this.dateTimeLabel.setText(getEventDateTimeDetails(event));
        this.pricePerTicketLabel.setText("$" + Double.toString(event.getPrice()));
        try {
            getImage(event);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TicketComponentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getEventDateTimeDetails(Events event) {
        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        String time = event.getEventDates().getEventStartData().getEventLocalTime();
        
        return date + " " + time;
        
    }
    
    private void getImage(Events event) throws FileNotFoundException {
        eventImage = new Image(event.getImageUrl());
        this.eventImageView.setImage(eventImage);
    }

    
}
