/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.Database.DatabaseInterface;
import Classes.Database.PurchasedEvent;
import Views.DashboardView.DashboardViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class PurchasedTicketsViewComponentController implements Initializable {

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
    }

    

}
