package Views.LandingView;

/**
 * @Course: SDEV 450 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Views.LandingView
 * @Date: Nov 9, 2019
 * @Subclass LandingViewController Description:
 */
//Imports
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
//Begin Subclass LandingViewController

public class LandingViewController implements Initializable {

    private final int ROWS_OF_NEARBY_EVENTS_TO_DISPLAY = 2;
    private final int ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY = 2;

    DashboardViewController dvc;
    List<Event> purchasedEvents = new ArrayList<>();
    List<HBox> purchasedComponents = new ArrayList<>();
    List<TicketMasterEvent.Embedded.Events> nearEvents = new ArrayList<>();
    List<HBox> nearComponents = new ArrayList<>();
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

    //@FXML
    //private HBox botHBox1;
    //@FXML
    //private HBox botHBox2;
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
        loadNearEvents();
    }

    /**
     *
     * @param userID
     */
    private void loadMyEvents(long userID) {
        //temporary event
        //Event event = new Event("1A0ZA_4GkecKxIM");

        //TODO load events user has purchased
        di.init();
        ResultSet rs = di.retrieveRS("SELECT EventId FROM UsersEvents WHERE"
                + " UserId = '" + userID + "'");
        try {
            int i = 0;
            while (rs.next()) { //fill up purchasedEvents
                if (i > ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY * 2) {
                    break;
                }
                purchasedEvents.add(new Event(rs.getString("EventId")));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        int n = purchasedEvents.size();
        if (n > ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY * 2) {
            n = ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY * 2;
        }
        for (int i = 0; i < n; i += 2) {
            HBox newRow = new HBox();
            for (int j = i; j < i + 2; j++) {
                if (j >= n) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TicketComponent/HTicketComponent.fxml"));
                    HBox container = loader.load();
                    newRow.getChildren().add(container);
                    HTicketComponentController temp = loader.getController();
                    temp.setEventData(purchasedEvents.get(j), dvc);
                } catch (IOException e) {
                    System.out.println(e);
                }

            }
            topVBox.getChildren().add(newRow);
        }

    }

    /**
     *
     * @param userName
     */
    private void loadNearEvents() {
        // Get a list of events from the API using a specific zip code for now - 37201
        nearEvents = tma.findEvents("", "1", "37201").getEmbeddedEvents().getEvents();
        int n = nearEvents.size();
        if (n > ROWS_OF_NEARBY_EVENTS_TO_DISPLAY * 2) {
            n = ROWS_OF_NEARBY_EVENTS_TO_DISPLAY * 2;
        }
        for (int i = 0; i < n; i += 2) {
            HBox newRow = new HBox();
            for (int j = i; j < i + 2; j++) {
                if (j >= n) {
                    break;
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TicketComponent/HTicketComponent.fxml"));
                    HBox container = loader.load();
                    newRow.getChildren().add(container);
                    HTicketComponentController temp = loader.getController();
                    temp.setEventData(nearEvents.get(j), null, dvc);
                } catch (IOException e) {
                    System.out.println(e);
                }

            }
            botVBox.getChildren().add(newRow);
        }
    }

} //End Subclass LandingViewController
