/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.Database.PurchasedEvent;
import Classes.Database.User;
import Classes.Email.Messages;
import Classes.Email.SendEmail;
import Views.DashboardView.DashboardViewController;
import Views.PurchasedTicketsDetailsView.PurchasedTicketsDetailsViewController;
import Views.PurchasingView.PurchasingViewController;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class PurchasedTicketsViewComponentController extends TicketComponent implements Initializable {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDateLabel;
    @FXML
    private Button viewDetailsButton;
    @FXML
    private Button resendTicketsButton;

    PurchasedEvent pEvent;
    DashboardViewController dvc;
    User user;
    String seats;
    String price = "$100.00"; // TODO delete once price is in pEvent

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setEventData(PurchasedEvent event, DashboardViewController dvc) {
        this.pEvent = event;
        this.dvc = dvc;
        this.eventNameLabel.setText(pEvent.getEventName());
        this.eventDateLabel.setText(pEvent.getEventDate().toString());
        setSeatsString();
        
        // TODO GET URL FROM pEVENT
        loadImage("https://s1.ticketm.net/dam/a/ac8/aedc8214-7ae1-4f2b-84b3-cec13763bac8_1051221_TABLET_LANDSCAPE_LARGE_16_9.jpg");
    }
    public void setUser(User user) {
        this.user = user;
        
    }
    
    public void openDetailsView() throws IOException, NoSuchAlgorithmException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Views/PurchasedTicketsDetailsView/PurchasedTicketsDetailsView.fxml"));
        //Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        // set the user to the dashboard view controller to maintain state
        PurchasedTicketsDetailsViewController controller = fxmlLoader.<PurchasedTicketsDetailsViewController>getController();
        controller.setEventData(pEvent);
        controller.setUser(user);
        Stage stage = new Stage();

        stage.setTitle(pEvent.getEventName() + " Details");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.show();
    }
    private String setSeatsString() {

        
        for (int i = 0; i < pEvent.getSeats().size(); i++) {
            String seat = pEvent.getSeats().get(i).getSeat();
            String row = pEvent.getSeats().get(i).getRow();
            seats += String.format("Seat: %s - Row: %s\n", seat, row);
        }

        return seats;

    }
    
    public void resendTicket() {
        // Replace seats and price
        String message = Messages.purchasedEventMessage(pEvent.getEventName(), seats, price, user.getUsername());
            try {
                SendEmail newEmail = new SendEmail("chidi117@gmail.com", "Ticket Purchase", message, pEvent.getEventName());
            } catch (MessagingException ex) {
                Logger.getLogger(PurchasingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    

}
