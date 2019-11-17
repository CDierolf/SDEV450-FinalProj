/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.TicketComponent;

import Classes.Database.DatabaseInterface;
import Classes.Database.Event;
import Views.DashboardView.DashboardViewController;
import Views.LandingView.LandingViewController;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    Event DBEvent;
    DatabaseInterface di = new DatabaseInterface();
    DashboardViewController dvc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setEventData(DashboardViewController dvc) {
        this.dvc = dvc;
    }

}
