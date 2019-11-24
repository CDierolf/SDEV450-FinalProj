/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PurchasedTicketsDetailsView;

import Classes.Database.PurchasedEvent;
import Classes.Database.User;
import Classes.Email.Messages;
import Classes.Email.SendEmail;
import Views.PurchasingView.PurchasingViewController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class PurchasedTicketsDetailsViewController implements Initializable {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDateTimeLabel;
    @FXML
    private Label eventPriceLabel;
    @FXML
    private Label eventSeatsLabel;
    @FXML
    private Button resendTicketsButton;
    @FXML
    private Button closeButton;
    @FXML
    private Label venueNameLabel;
    @FXML
    private Label venueCityStateLabel;
    @FXML
    private ImageView eventImageView;

    PurchasedEvent pEvent;
    User user;
    String seats = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setEventData(PurchasedEvent pEvent) {
        this.pEvent = pEvent;
        this.eventNameLabel.setText(pEvent.getEventName());
        this.eventDateTimeLabel.setText(pEvent.getEventDate().toString());
        this.eventPriceLabel.setText("$" + String.format("%.2f", pEvent.getEventPrice()));
        this.venueNameLabel.setText(pEvent.getVenue().getVenueName());
        this.venueCityStateLabel.setText(String.format("%s, %s", pEvent.getVenue().getVenueCity(),
                pEvent.getVenue().getVenueState()));
        this.eventSeatsLabel.setText(setSeatLabel());

        // TODO USE URL FROM pEVENT
        loadImage(pEvent.getEventImageUrl());

    }

    public void setUser(User user) {
        this.user = user;
    }

    private String setSeatLabel() {

        for (int i = 0; i < pEvent.getSeats().size(); i++) {
            String seat = pEvent.getSeats().get(i).getSeat();
            String row = pEvent.getSeats().get(i).getRow();
            seats += String.format("Seat: %s - Row: %s\n", seat, row);
        }

        return seats;

    }

    public void closeButtonAction() {
        Stage stage = (Stage) this.closeButton.getScene().getWindow();
        stage.close();

    }

    public void resendTicket() {
        String message = Messages.purchasedEventMessage(pEvent.getEventName(), seats, this.eventPriceLabel.getText(), user.getUsername());
        try {
            SendEmail newEmail = new SendEmail("chidi117@gmail.com", "Ticket Purchase", message, pEvent.getEventName());
        } catch (MessagingException ex) {
            Logger.getLogger(PurchasingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // For purchasedticketsviewcomponentcontroller image by pEvent url
    public void loadImage(String url) {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // call getImages asynchronously
                getImage(url);
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

    public void getImage(String url) {
        if (url != null) {
            this.eventImageView.setImage(new Image(url));
        }
    }

}
