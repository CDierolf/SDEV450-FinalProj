/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PurchasedTicketsView;

import Classes.Database.dao.PurchasedTicketsViewDAO;
import Classes.Objects.PurchasedEvent;
import Classes.Objects.PurchasedEventSorter;
import Classes.Objects.Seat;
import Classes.Objects.User;
import Classes.Objects.Venue;
import Views.DashboardView.DashboardViewController;
import Views.TicketComponent.PurchasedTicketsViewComponentController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Christopher Dierolf
 */
public class PurchasedTicketsViewController implements Initializable {

    @FXML
    private VBox eventVBox;

    DashboardViewController dvc;
    List<PurchasedTicketsViewComponentController> tcElements = new ArrayList<>();
    List<HBox> ticketComponents = new ArrayList<>();

    ArrayList<PurchasedEvent> eventData = new ArrayList<>();
    User user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDashboardControllerAndUser(DashboardViewController dvc, User user) throws SQLException, IOException {
        this.dvc = dvc;
        this.user = user;
        getEventData();
        loadTicketComponents();
        loadEventDataToComponents();
    }
    

    public void loadTicketComponents() {
        for (int i = 0; i < eventData.size(); i++) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/Views/TicketComponent/PurchasedTicketsViewComponent.fxml"));
                    HBox ticketComponentPane = loader.load();

                    ticketComponents.add(ticketComponentPane);
                    PurchasedTicketsViewComponentController tcCtrl = loader.getController();
                    tcElements.add(tcCtrl);

                } catch (IOException ex) {
                    Logger.getLogger(PurchasedTicketsViewComponentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }

    private void loadEventDataToComponents() throws FileNotFoundException, IOException, SQLException {

        for (int i = 0; i < ticketComponents.size(); i++) {
            tcElements.get(i).setEventData(eventData.get(i), dvc);
            tcElements.get(i).setUser(this.user);
            //tcElements.get(i).loadImage(); now called inside TicketComponent
            displayEventComponents(ticketComponents.get(i));
        }
    }

    private void displayEventComponents(HBox pane) throws IOException, SQLException {

        eventVBox.getChildren().add(pane);
        eventVBox.setSpacing(20);

    }

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
                while (rs.next()) {
                    currentEvent = rs.getString("EventName");
                    if (!currentEvent.equalsIgnoreCase(lastEvent)) {
                        if (!lastEvent.equals("")) {
                            eventData.add(pEvent);
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
                eventData.add(pEvent);
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                dao.close();
            }
        }
        
        PurchasedEventSorter pEventSorter = new PurchasedEventSorter(this.eventData);
        this.eventData = pEventSorter.getSortedPurchasedEvents();

    }
}
