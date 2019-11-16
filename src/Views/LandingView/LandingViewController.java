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
import Classes.Database.Event;
import java.net.URL;
import java.util.ResourceBundle;
import Views.DashboardView.DashboardViewController;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import Classes.Database.DatabaseInterface;
import java.io.IOException;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
//Begin Subclass LandingViewController

public class LandingViewController implements Initializable {

    DashboardViewController dvc;
    List<TicketMasterEvent.Embedded.Events> nearEvents = new ArrayList<>();
    List<Event> purchasedEvents = new ArrayList<>();
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

    @FXML
    private HBox topHBox1;
    
    @FXML
    private HBox topHBox2;
    
    @FXML
    private HBox botHBox1;
    
    @FXML
    private HBox botHBox2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     *
     * @param dvc
     */
    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
        loadMyEvents(dvc.getUser().getUserID());
    }

    private void loadMyEvents(long userID) {
        //FIXME temporary event
        Event event = new Event("1A0ZA_4GkecKxIM");
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/TicketComponent/HTicketComponent.fxml"));

            HBox container = loader.load();
            topHBox1.getChildren().add(container);
            HTicketComponentController temp = loader.getController();
            temp.setEventData(event);
        } catch (IOException e) {
            System.out.println(e);
        }

        //TODO load events user has purchased
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
        

    }

    private void loadNearEvents(String userName) {
        // Get a list of events from the API using a random LA zip code for now
        nearEvents = tma.findEvents("", "1", "37201").getEmbeddedEvents().getEvents();

    }

} //End Subclass LandingViewController
