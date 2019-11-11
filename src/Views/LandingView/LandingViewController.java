package Views.LandingView;

/**
 * @Course: SDEV 450 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Views.LandingView
 * @Date: Nov 9, 2019
 * @Subclass LandingViewController Description:
 */
//Imports
import Views.DashboardView.DashboardViewController;
import Views.TicketComponent.HTicketComponentController;
import Classes.APIs.TicketMaster.TicketMasterAPI;
import Classes.APIs.TicketMaster.TicketMasterEvent;
import java.net.URL;
import java.util.ResourceBundle;
import Views.DashboardView.DashboardViewController;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import Classes.Database.DatabaseInterface;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
//Begin Subclass LandingViewController

public class LandingViewController implements Initializable {

    DashboardViewController dvc;
    List<TicketMasterEvent.Embedded.Events> nearEvents = new ArrayList<>();
    TicketMasterAPI tma = new TicketMasterAPI();
    DatabaseInterface di = new DatabaseInterface();
    @FXML
    private VBox outerVBox;
    @FXML
    private VBox topVBox;
    @FXML
    private Label topLabel;
    @FXML
    private VBox botVBox;
    @FXML
    private Label botLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadMyEvents("34566");
    }

    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }

    private void loadMyEvents(String userID) {
        //TODO get user ID from logged in user
        di.init();
        ResultSet rs = di.retrieveRS("SELECT EventId FROM UsersEvents WHERE"
                + " UserId = '" + userID + "'");
        try {
            while (rs.next()) {
                System.out.println(rs.getString("UserId"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        //may be hacky because component uses API event instead of a base class

    }

    private void loadNearEvents(String userName) {
        // Get a list of events from the API using a random LA zip code for now
        nearEvents = tma.findEvents("", "1", "90805").getEmbeddedEvents().getEvents();

    }

} //End Subclass LandingViewController
