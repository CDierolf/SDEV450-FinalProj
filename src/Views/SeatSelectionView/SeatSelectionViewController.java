/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.SeatSelectionView;

import Classes.Utilities.Alerts;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Objects.User;
import Classes.Utilities.Enums.ViewEnum;
import Views.DashboardView.DashboardViewController;
import Views.PurchasingView.PurchasingViewController;
import Views.SeatMaps.Venue.SeatMapSeat;
import Views.SeatMaps.Venue.SeatMapVenue;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class SeatSelectionViewController implements Initializable {

    // SeatMapVenue FXML Objects
    @FXML
    private Pane venuePane;
    @FXML
    private VBox eventVBox;
    @FXML
    private Label lblEventName;
    @FXML
    private Label lblEventDate;
    @FXML
    private Label lblEventSeatPrice;
    @FXML
    private Label lblTotal;
    @FXML
    private Button backButton;
    @FXML
    private Pane purchasingPane;

    private DashboardViewController dvc;

    private Events event;
    private ArrayList<SeatMapSeat> selectedSeats;
    private ArrayList<Label> selectedSeatsLabels;
    private double total;
    private User user;
    private ViewEnum view;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Events getEvent() {
        return event;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    

    // Sets the event data for the View
    public void setEventData(Events e) {
        this.event = e;
        //loadVenue(e);
        // Call threaded loadVenue(e) method.
        loadVenueSeating(e);
        try {
            loadEventData(e);
        } catch (ParseException ex) {
            Logger.getLogger(SeatSelectionViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setView(ViewEnum view) {
        this.view = view;
    }

    // Set venue callback
    // Sets the reference back to the DashboardViewController instance
    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }

    public DashboardViewController getDashboardController() {
        return dvc;
    }

    // Load event data details and show the venue seating.
    public void loadVenue(Events event) {

        SeatMapVenue ven = new SeatMapVenue(event, this);
        venuePane.getChildren().add(ven);
        venuePane.setVisible(true);
        //isVenueLoaded = true;
    }

    
    // Loading SeatMapVenue on separate thread
    private void loadVenueSeating(Events e) {
        Thread thread = new Thread(() -> {
            Runnable run = () -> {
                loadVenue(e);
            };
            Platform.runLater(run);
        });

        thread.setDaemon(true);
        thread.start();
    }


// Load in event data
private void loadEventData(Events e) throws ParseException {
        lblEventName.setText(e.getName());

        String unformattedDate = e.getEventDates().getEventStartData().getEventLocalDate();
        String unformattedTime = e.getEventDates().getEventStartData().getEventLocalTime();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(unformattedDate);
            Date time = new SimpleDateFormat("HH:mm:ss").parse(unformattedTime);
            String formattedDate = new SimpleDateFormat("EEE, MMM d").format(date);
            String formattedTime = new SimpleDateFormat("h:mm a").format(time);
            lblEventDate.setText(formattedDate
                    + " at " + formattedTime);
        } catch (Exception ex) {
            Alerts.genericAlert("Date error", "Date error", "Date and time is not set for this show yet. Try again at a later date.").showAndWait();
        }
        try {
            double seatValue = Double.valueOf(event.getPrice());
            lblEventSeatPrice.setText("Seat Price: $" + String.format("%.2f", seatValue));
        } catch (Exception ex) {
            Alerts.genericAlert("Price error", "Price error", "Price is not set for this show yet. Try again at a later date.").showAndWait();
        }
        selectedSeats = new ArrayList<>();
        selectedSeatsLabels = new ArrayList<>();
    }

    // SeatMapSeat is selected
    public void seatSelected(SeatMapSeat seat) {
        Label seatLabel = new Label(seat.getDescription());
        selectedSeats.add(seat);
        selectedSeatsLabels.add(seatLabel);
        eventVBox.getChildren().add(seatLabel);

        updateTotal(true);
    }

    // SeatMapSeat is unselected
    public void seatUnselected(SeatMapSeat seat) {
        Label labelToRemove = new Label();
        for (Label label : selectedSeatsLabels) {
            if (label.getText().equals(seat.getDescription())) {
                eventVBox.getChildren().remove(label);
                labelToRemove = label;
            }
        }
        selectedSeatsLabels.remove(labelToRemove);
        selectedSeats.remove(seat);

        updateTotal(false);
    }

    // Update total label
    private void updateTotal(boolean didIncrease) {
        // Update total for seats
        double seatValue = Double.valueOf(event.getPrice());
        if (didIncrease) {
            total += seatValue;
        } else {
            total -= seatValue;
        }
        // Format total and display on label
        lblTotal.setText("Total: $" + String.format("%.2f", total));
    }
    
    
    // Return currently selected seats
    public ArrayList<SeatMapSeat> getSelectedSeats() {
        return selectedSeats;
    }
    
    // Return total of selected seats
    public String getPurchaseTotal() {
        return String.format("%.2f", total);
    }

    // Load Purchasing View
    public void loadPurchasingView() throws IOException {
        venuePane.setVisible(false);
        purchasingPane.setDisable(false);
        
        // Load FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/PurchasingView/PurchasingView.fxml"));
        purchasingPane.getChildren().add(loader.load());
        
        // Set Attributes
        PurchasingViewController pvc = loader.getController();
        pvc.setSeatSelectionViewController(this);
        pvc.setupValidation();
        pvc.setDashboardController(this.getDashboardController());
        pvc.setEvent(this.getEvent()); // set the event to the purchase controller
        pvc.setUser(this.getUser()); // set the current user to the purchase controller
    }



    public void setSelectedSeats(ArrayList<SeatMapSeat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }
    
    // Unload Purchasing View
    public void unloadPurchasingView() {
        purchasingPane.getChildren().clear();
        purchasingPane.setDisable(true);
        venuePane.setVisible(true);
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
    }

    // Calls the unloadSeatSelectionView() method in DashboardViewController instance
    // Sets the FindEventsView visibility to true;
    public void goBackToPreviousView() throws IOException, SQLException {
        
        if (view == ViewEnum.FIND_EVENTS_VIEW) {
            dvc.toggleEventViewVisiblity(true);
            dvc.unloadSeatSelectionView();
        } else if (view == ViewEnum.LANDING_VIEW) {
            dvc.loadLandingView();
            dvc.unloadSeatSelectionView();
        }
    }

}
