/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.FindEventsView;

import Classes.APIs.TicketMaster.TicketMasterAPI;
import Classes.APIs.TicketMaster.TicketMasterEvent;
import Classes.Utilities.Validation;
import Views.DashboardView.DashboardViewController;
import Views.TicketComponent.TicketComponentController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class FindEventsViewController implements Initializable {

    private final int MAX_ALLOWABLE_EVENTS_PER_PAGE = 20;

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
    private TextField postalCodeTextField;
    @FXML
    private Button previousPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private Label pageLabel;
    @FXML
    private Label postalCodeWarningLabel;
    @FXML
    private Label noEventsLabel;

    List<TicketMasterEvent.Embedded.Events> events = new ArrayList<>();
    List<TicketComponentController> tcElements = new ArrayList<>();
    List<VBox> ticketComponents = new ArrayList<>();
    TicketMasterAPI tma = new TicketMasterAPI();
    DashboardViewController dvc;
    private int currentPage = 0;
    private int numEvents = 0;
    private String currentKeyword = "";
    private String currentPostalCode = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pageLabel.setText(Integer.toString(currentPage));
        this.postalCodeWarningLabel.setVisible(false);
        this.previousPageButton.setDisable(true);
        this.nextPageButton.setDisable(true);
        this.noEventsLabel.setVisible(false);
    }

    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }

    private void loadEvents(String eventKeyword, String pageNumber, String postalCode) throws IOException, Exception {

        // Save the current keyword for pagination
        this.currentKeyword = this.searchTextField.getText();
        this.currentPostalCode = this.postalCodeTextField.getText();
        this.noEventsLabel.setVisible(false);
        Object o = tma.findEvents(eventKeyword, pageNumber, postalCode);
        

        if (o == null) {
            System.out.println("NULL");
            this.noEventsLabel.setVisible(true);
        } else {
            events = tma.findEvents(eventKeyword, pageNumber, postalCode).getEmbeddedEvents().getEvents();

            // Get a list of events from the API.
            // Save the number of events returned.
            numEvents = events.size();

            // Set pagination advancability
            if (!canGotoNextPage()) {
                this.nextPageButton.setDisable(true);
            } else {
                this.nextPageButton.setDisable(false);
            }

            // For each event, create a TicketComponent object
            for (int i = 0; i < events.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TicketComponent/TicketComponent.fxml"));
                    VBox ticketComponentPane = loader.load();

                    ticketComponentPane.setId(Integer.toString(i));

                    ticketComponents.add(ticketComponentPane);
                    TicketComponentController tcCtrl = loader.getController();
                    tcElements.add(tcCtrl);

                } catch (IOException ex) {
                    Logger.getLogger(FindEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Run the loadEventComponents() method
            // in a separate background thread
            Thread thread = new Thread(() -> {
                Runnable run = () -> {
                    try {
                        loadEventComponents();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(FindEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };
                Platform.runLater(run);
            });

            thread.setDaemon(true);
            thread.start();
        }

    }

    // Load all data into the ticketcomponent controllers
    // Get the images (runs concurrently from loadEvents()
    private void loadEventComponents() throws FileNotFoundException {

        for (int i = 0; i < ticketComponents.size(); i++) {
            tcElements.get(i).setEventData(events.get(i), FindEventsViewController.this, dvc);
            tcElements.get(i).loadImage();
            displayEventComponents(ticketComponents.get(i), i);
        }
    }

    // Display the components to the screen.
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

        if (validatePostalCode()) {
            this.postalCodeWarningLabel.setVisible(false);
            clearEvents();
            resetPageNumber();
            loadEvents(this.searchTextField.getText(), Integer.toString(this.currentPage), this.postalCodeTextField.getText());
        } else {
            this.postalCodeWarningLabel.setVisible(true);
            this.postalCodeWarningLabel.setText("Inavlid postal code");

        }
    }

    public void gotoNextPage() throws IOException, Exception {

        if (canGotoNextPage()) {
            clearEvents();
            this.currentPage++;
            this.pageLabel.setText(Integer.toString(this.currentPage));
            loadEvents(currentKeyword, Integer.toString(currentPage), currentPostalCode);
            this.previousPageButton.setDisable(false);
        } else {
            this.nextPageButton.setDisable(true);
        }
    }

    public void gotoPreviousPage() throws Exception {
        if (currentPage == 0) {
            // Can't go back any farther
            this.previousPageButton.setDisable(true);
        } else {
            this.currentPage--;
            this.pageLabel.setText(Integer.toString(this.currentPage));
            clearEvents();
            loadEvents(this.searchTextField.getText(), Integer.toString(this.currentPage), this.postalCodeTextField.getText());
            // Recall API with currentKeyword and currentPage
        }
    }

    private void resetPageNumber() {
        this.currentPage = 0;
        this.pageLabel.setText(Integer.toString(currentPage));
    }

    private void clearEvents() {
        this.events.clear();
        this.ticketComponents.clear();
        this.tcElements.clear();
        leftVBox.getChildren().clear();
        centerVBox.getChildren().clear();
        rightVBox.getChildren().clear();
        rightMostVBox.getChildren().clear();
    }

    private boolean validatePostalCode() {
        boolean isValidPostalCode = false;
        if (this.postalCodeTextField.getText().isEmpty()) {
            return true;
        }

        if (!this.postalCodeTextField.getText().isEmpty()) {
            isValidPostalCode = Validation.validateIntegerLength(this.postalCodeTextField.getText(), 5, "-Postal Code-", false);
        }

        return isValidPostalCode;
    }

    private boolean canGotoNextPage() {

        boolean canAdvance = false;
        if (numEvents < this.MAX_ALLOWABLE_EVENTS_PER_PAGE) {
            canAdvance = false;
        }

        if (numEvents >= this.MAX_ALLOWABLE_EVENTS_PER_PAGE) {
            canAdvance = true;
        }

        return canAdvance;
    }
}
