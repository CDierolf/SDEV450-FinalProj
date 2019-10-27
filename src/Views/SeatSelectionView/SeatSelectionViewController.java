/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.SeatSelectionView;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.DashboardView.DashboardViewController;
import Views.SeatMaps.Venue.Venue;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class SeatSelectionViewController implements Initializable {

        // Venue FXML Objects
    @FXML
    private Pane venuePane;
    @FXML
    private HBox venueHBox;
    @FXML
    private Label lblEventName;
    @FXML
    private Label lblEventDate;
    @FXML
    private Label lblEventTime;
    @FXML
    private Button backButton;
    
    private DashboardViewController dvc;
    
    private Events event;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("In seat selection view");
        
    }   
    
    // Sets the event data for the View
    public void setEventData(Events e) {
        this.event = e;
        loadVenue(e);
    }
    
    // Sets the reference back to the DashboardViewController instance
    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }
    
    // Load event data details and show the venue seating.
    public void loadVenue(Events event) {
        System.out.println("Running loadEvent() method.");
        System.out.println(this.event);
        System.out.println(event.getName());
        System.out.println(event.getEventDates().getEventStartData().getEventLocalDate());
        System.out.println(event.getEventDates().getEventStartData().getEventLocalTime());

        Venue ven = new Venue(event);
        venuePane.getChildren().add(ven);
        venuePane.setVisible(true);
        //isVenueLoaded = true;
    }

    // TODO REFACTOR
    private void unloadVenue() {
        venuePane.setVisible(false); // Hide venue pane
//        if (isVenueLoaded) {
//            venueHBox.getChildren().remove(venueHBox.getChildren().size() - 1); // Remove venue
//            isVenueLoaded = false;
//        }
        lblEventName.setText("");
        lblEventDate.setText("");
        lblEventTime.setText("");
    }
    
    // Calls the unloadSeatSelectionView() method in DashboardViewController instance
    // Sets the FindEventsView visibility to true;
    public void goBackToFindEventsView() {
        dvc.toggleEventViewVisiblity(true);
        dvc.unloadSeatSelectionView();
    }
    
    
}