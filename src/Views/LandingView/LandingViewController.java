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
import java.net.URL;
import java.util.ResourceBundle;
import Views.DashboardView.DashboardViewController;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import Classes.Database.dao.LandingViewDAO;
import Classes.Database.dao.PurchasedTicketsViewDAO;
import Classes.Objects.PurchasedEvent;
import Classes.Objects.Seat;
import Classes.Objects.Venue;
import Classes.Utilities.Enums.ViewEnum;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.*;
//Begin Subclass LandingViewController

public class LandingViewController implements Initializable {

    private final int ROWS_OF_NEARBY_EVENTS_TO_DISPLAY = 2;
    private final int ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY = 2;

    DashboardViewController dvc;
    List<PurchasedEvent> purchasedEvents = new ArrayList<>();
    List<HBox> purchasedComponents = new ArrayList<>();
    List<TicketMasterEvent.Embedded.Events> nearEvents = new ArrayList<>();
    List<HBox> nearComponents = new ArrayList<>();
    TicketMasterAPI tma = new TicketMasterAPI();
    LandingViewDAO dao = new Classes.Database.dao.LandingViewDAO();
    PurchasedTicketsViewDAO pEventDao = new PurchasedTicketsViewDAO();
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
    public void setDashboardController(DashboardViewController dvc) throws SQLException {
        this.dvc = dvc;
        loadMyEvents(dvc.getUser().getUserID());
        loadNearEvents();
    }

    /**
     *
     * @param userID
     */
    public void getEventData() throws SQLException {
        PurchasedTicketsViewDAO dao = new PurchasedTicketsViewDAO();
        dao.init();
        ResultSet rs = dao.getMyEvents(dvc.getUser().getUserID());
        if (rs != null) {
            try {
                PurchasedEvent pEvent = null;
                Seat seat = new Seat();
                String lastEvent = "";
                String currentEvent = "";
                // get # of events based on ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY
                while (rs.next() && (purchasedEvents.size() < (ROWS_OF_PURCHASED_EVENTS_TO_DISPLAY * 2))) {
                    currentEvent = rs.getString("EventName");
                    if (!currentEvent.equalsIgnoreCase(lastEvent)) {
                        if (!lastEvent.equals("")) {
                            purchasedEvents.add(pEvent);
                        }
                        pEvent = new PurchasedEvent();
                        Venue venue = new Venue();
                        pEvent.setEventName(rs.getString("EventName"));
                        pEvent.setEventDate(rs.getDate("StartDate"));
                        pEvent.setEventTime(rs.getTime("StartTime"));
                        pEvent.setEventPrice(rs.getDouble("TotalPrice"));
                        pEvent.setEventImageUrl(rs.getString("image"));
                        venue.setVenueCity(rs.getString("venueCity"));
                        venue.setVenueState(rs.getString("venueState"));
                        venue.setVenueName(rs.getString("venueName"));
                        pEvent.setVenue(venue);
                        lastEvent = currentEvent;
                    }
                    seat = new Seat();
                    seat.setSeat(rs.getString("Seat"));
                    seat.setRow(rs.getString("Row"));
                    pEvent.addSeat(seat);
                }
                purchasedEvents.add(pEvent);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                dao.close();
            }
        }
    }

    private void loadMyEvents(long userID) throws SQLException {

        getEventData();

        int n = purchasedEvents.size();
        //display error if there are no events
        if (n == 0) {
            displayErrorTop();
            return;
        }
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
        try {
            // Get a list of events from the API using a specific zip code for now - 37201
            nearEvents = tma.findEvents("", "1", "37201").getEmbeddedEvents().getEvents();
        } catch (IOException ex) {
            Logger.getLogger(LandingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int n = nearEvents.size();
        if (n == 0) {
            displayErrorBot();
            return;
        }
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
                    temp.setView(ViewEnum.LANDING_VIEW);
                } catch (IOException e) {
                    System.out.println(e);
                }

            }
            botVBox.getChildren().add(newRow);
        }
    }

    private void displayErrorTop() {
        Label l = new Label("You haven't purchased any events yet");
        Button b = new Button("Find some events");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    dvc.loadFindEventsView();
                } catch (IOException ex) {
                    Logger.getLogger(LandingViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        topVBox.getChildren().addAll(l, b);
    }

    private void displayErrorBot() {
        Label l = new Label("You haven't purchased any events yet");
        Button b = new Button("Find some events");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    dvc.loadFindEventsView();
                } catch (IOException ex) {
                    Logger.getLogger(LandingViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        topVBox.getChildren().addAll(l, b);
    }

} //End Subclass LandingViewController
