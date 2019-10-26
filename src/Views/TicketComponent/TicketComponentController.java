/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.DashboardView.DashboardViewController;
import Views.FindEventsView.FindEventsViewController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
 *
 */
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
    
    // 
    public void setEventData(Events event, FindEventsViewController fevc, DashboardViewController dvc) {

        this.eventLabel.setText(event.getName());
        this.dateTimeLabel.setText(getEventDateTimeDetails(event));
        this.pricePerTicketLabel.setText("$" + Double.toString(event.getPrice()));

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

    // Load images in the background and display when available.
    public void loadImage() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // call getImages asynchronously
                getImage(event);
                return null;
            }
        };

        task.setOnSucceeded((WorkerStateEvent event1) -> {
            Void result = task.getValue();
        });

        Thread t = new Thread(task);
        t.setDaemon(true); 
        t.start();
    }

    public void getImage(Events event) throws FileNotFoundException {
        eventImageView.setImage(event.getEventImage());
    }

    // Event handler for "Puchase Tickets" button
    public void purchaseTickets() throws IOException {
        
        //        
        //        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        //        String time = event.getEventDates().getEventStartData().getEventLocalTime();
        //        System.out.println(event.getName() + " " + date + time);
        //        System.out.println(event);
        //
        
        // Load the SeatSelectionView
        dvc.loadSeatSelectionView(event);
        // Hide the FindEventsView
        dvc.toggleEventViewVisiblity(false);


    }
}
