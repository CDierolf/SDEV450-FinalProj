/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PurchasedTicketsView;

import Classes.Database.DatabaseInterface;
import Classes.Database.PurchasedEvent;
import Classes.Database.Seat;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class PurchasedTicketsViewController implements Initializable {

    @FXML
    private VBox eventVBox;

    DashboardViewController dvc;
    List<PurchasedTicketsViewComponentController> tcElements = new ArrayList<>();
    List<HBox> ticketComponents = new ArrayList<>();
    DatabaseInterface di = new DatabaseInterface();
    ArrayList<PurchasedEvent> eventData = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDashboardController(DashboardViewController dvc) throws SQLException, IOException {
        this.dvc = dvc;
        getEventData();
        loadTicketComponents();
        loadEventDataToComponents();
    }

    // TODO Fix
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

    // TODO Fix
    private void loadEventDataToComponents() throws FileNotFoundException, IOException, SQLException {

        for (int i = 0; i < ticketComponents.size(); i++) {
            tcElements.get(i).setEventData(eventData.get(i), dvc);
            //tcElements.get(i).loadImage(); now called inside TicketComponent
            displayEventComponents(ticketComponents.get(i));
        }
    }

    private void displayEventComponents(HBox pane) throws IOException, SQLException {

        eventVBox.getChildren().add(pane);
        eventVBox.setSpacing(20);

    }

    // This works. No changes needed. Use eventData List to pull users purchased events data including seat and row
    public void getEventData() throws SQLException {
        di.init();
        ArrayList<String> userValues = new ArrayList<>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<>();
        String Q1 = "{ call [usp_getAllEventSeatsForUser](?) }";
        userValues.add(Long.toString(dvc.getUser().getUserID()));
        dataTypes.add("string");
        ResultSet rs = null;
        try {// execute the stored proc
            rs = di.callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            Logger.getLogger(PurchasedTicketsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        pEvent.setEventName(rs.getString("EventName"));
                        pEvent.setEventDate(rs.getDate("StartDate"));
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
            }
        }

        System.out.println("FIRST EVENT: " + eventData.get(0).getEventName());
        System.out.println("FIRST EVENT SEATS: " + eventData.get(0).getSeats().size());
        System.out.println("Sec EVENT: " + eventData.get(1).getEventName());
        System.out.println("SEc EVENT SEATS: " + eventData.get(1).getSeats().size());
        System.out.println("Third EVENT: " + eventData.get(2).getEventName());
        System.out.println("Third EVENT SEATS: " + eventData.get(2).getSeats().size());
        System.out.println("Fourth EVENT: " + eventData.get(3).getEventName());
        System.out.println("Fouth EVENT SEATS: " + eventData.get(3).getSeats().size());
//        for (int i = 0; i < eventData.size(); i++) {
//            System.out.println("EVENT: " + eventData.get(i).getEventName());
//            System.out.println("EVENT DATE: " + eventData.get(i).getEventDate());
//            for (int j = 0; j < eventData.get(i).getSeats().size(); j++) {
//                System.out.print("SEAT: " + eventData.get(i).getSeats().get(j).getSeat());
//                System.out.println(" ROW: " + eventData.get(i).getSeats().get(j).getRow());
//            }
//        }

    }
}
