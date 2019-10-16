/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.DashboardView;

import Classes.APIs.TicketMaster.TicketMasterAPI;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.SeatMaps.Venue.Venue;
import Views.TicketComponent.TicketComponentController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class DashboardViewController implements Initializable {

    // Dashboard UI Objects
    @FXML
    private VBox leftVBox;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox rightMostVBox;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    
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
    private boolean isVenueLoaded;

    List<Events> events = new ArrayList<>();
    List<Image> eventImages = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Not implemented.
    }

//    private Image downloadImage(Events event) throws InterruptedException, ExecutionException {
//        Image image;
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        Future future = executorService.submit(() -> {
//            Image image1;
//            image1 = new Image(event.getImageUrl());
//            return image1;
//        });
//
//        image = (Image) future.get();
//        executorService.shutdown();
//        return image;
//    }
    private void loadEvents(String eventKeyword, String pageNumber) throws IOException {
        
        unloadVenue(); // Unload venue view if shown
        
        TicketMasterAPI tma = new TicketMasterAPI();

        events = tma.findEvents(eventKeyword, pageNumber).getEmbeddedEvents().getEvents();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < events.size(); i++) {

                    try {
                        
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/Views/TicketComponent/TicketComponent.fxml"));
                        VBox pane = loader.load();
                        pane.setId(Integer.toString(i));
                        TicketComponentController tcCtrl = loader.getController();
                        
                        try {
                            tcCtrl.getEvent(events.get(i), DashboardViewController.this);
                        } catch (ExecutionException | InterruptedException ex) {
                            Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        // Sort the Views
                        switch (i % 4) {
                            case 0:
                                leftVBox.getChildren().add(pane);
                                leftVBox.setSpacing(10);
                                break;
                            case 1:
                                centerVBox.getChildren().add(pane);
                                centerVBox.setSpacing(10);
                                break;
                            case 2:
                                rightVBox.getChildren().add(pane);
                                rightVBox.setSpacing(10);
                                break;
                            case 3:
                                rightMostVBox.getChildren().add(pane);
                                rightMostVBox.setSpacing(10);
                                break;
                            default:
                                break;
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });
    }

    public void getEvents() throws IOException {
        clearEvents();
        loadEvents(this.searchTextField.getText(), "1");
    }

    private void clearEvents() {
        this.events.clear();
        leftVBox.getChildren().clear();
        centerVBox.getChildren().clear();
        rightVBox.getChildren().clear();
        rightMostVBox.getChildren().clear();
    }
    
    public void loadVenue(Events event) {
        System.out.println("Running loadEvent() method.");
        
        // Clear ticket components from view
        clearEvents();
        
        // Load venue pane
        lblEventName.setText(event.getName());
        lblEventDate.setText(event.getEventDates().getEventStartData().getEventLocalDate());
        lblEventTime.setText(event.getEventDates().getEventStartData().getEventLocalTime());
        Venue ven = new Venue(1);
        venueHBox.getChildren().add(ven);
        venuePane.setVisible(true);
        isVenueLoaded = true;
    }
    
    private void unloadVenue() {
        venuePane.setVisible(false); // Hide venue pane
        if (isVenueLoaded) {
            venueHBox.getChildren().remove(venueHBox.getChildren().size()-1); // Remove venue
            isVenueLoaded = false;
        }
        lblEventName.setText("");
        lblEventDate.setText("");
        lblEventTime.setText("");
    }
}
