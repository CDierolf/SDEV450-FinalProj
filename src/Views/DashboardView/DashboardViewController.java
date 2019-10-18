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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.FileNotFoundException;
import javafx.application.Platform;
import javafx.concurrent.Task;

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
    @FXML
    private Button previousPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private Label pageLabel;

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
    private Button find;
    private boolean isVenueLoaded;

    List<Events> events = new ArrayList<>();
    List<TicketComponentController> tcElements = new ArrayList<>();
    List<VBox> eventComponents = new ArrayList<>();
    private int currentPage = 0;
    private String currentKeyword = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pageLabel.setText(Integer.toString(currentPage));
    }

    private void loadEvents(String eventKeyword, String pageNumber) throws IOException, Exception {

        unloadVenue(); // Unload venue view if shown

        // Save the current keyword for pagination
        this.currentKeyword = this.searchTextField.getText();

        TicketMasterAPI tma = new TicketMasterAPI();

        events = tma.findEvents(eventKeyword, pageNumber).getEmbeddedEvents().getEvents();

        for (int i = 0; i < events.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TicketComponent/TicketComponent.fxml"));
                VBox ticketComponentPane = loader.load();

                ticketComponentPane.setId(Integer.toString(i));

                eventComponents.add(ticketComponentPane);
                TicketComponentController tcCtrl = loader.getController();
                tcElements.add(tcCtrl);

            } catch (IOException ex) {
                Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            loadEventComponents();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                };
                Platform.runLater(run);
            }

        });

        thread.setDaemon(true);
        thread.start();
    }

    public void butthole() {
        System.out.println("butthole");
    }

    private void loadEventComponents() throws FileNotFoundException {

        for (int i = 0; i < eventComponents.size(); i++) {
            tcElements.get(i).getEvent(events.get(i), DashboardViewController.this);
            tcElements.get(i).loadImage();
            displayEventComponents(eventComponents.get(i), i);

        }

    }

    private void displayEventComponents(VBox pane, int i) {
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
    }

    public void getEvents() throws IOException, Exception {
        clearEvents();
        loadEvents(this.searchTextField.getText(), Integer.toString(this.currentPage));
    }

    public void gotoNextPage() throws IOException, Exception {
        clearEvents();
        loadEvents(currentKeyword, Integer.toString(currentPage));
        if (events == null) {
            System.out.println("SHeeeeeet");
        } else {
            this.currentPage++;
            this.pageLabel.setText(Integer.toString(this.currentPage));
        }
    }

    public void gotoPreviousPage() {
        if (currentPage == 1) {
            // Can't go back any farther
            System.out.println("Unable to go back anymore.");
        } else {
            this.currentPage--;
            // Recall API with currentKeyword and currentPage
        }
    }

    private void clearEvents() {
        this.events.clear();
        this.eventComponents.clear();
        this.tcElements.clear();
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
            venueHBox.getChildren().remove(venueHBox.getChildren().size() - 1); // Remove venue
            isVenueLoaded = false;
        }
        lblEventName.setText("");
        lblEventDate.setText("");
        lblEventTime.setText("");
    }

}
