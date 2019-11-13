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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        //loadMyEvents();        
    }

    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }

    public void loadMyEvents() {
        di.init();

        ArrayList<String> userValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        String Q1 = "{ call [usp_UsersEventsSelect](?) }";
        userValues.add(Long.toString(dvc.getUser().getUserID()));
        dataTypes.add("int");
        ResultSet rs = null;
        /*
        	SELECT min(u.[UserEventId]), e.[EventId],  [OrderDate], [Tax] , e.eventname, e.startDate, count(*) as SeatCount
	FROM   [dbo].[UsersEvents] u
	inner join usereventseats S 
	on u.UserEventId=s.UserEventId
	INNER JOIN events e
	ON u.EventId = e.eventid

	WHERE  [UserId] = @userid
	GROUP BY e.eventid, u.UserEventId, orderdate, tax,e.eventname, e.startDate
        */
        try {
            rs = di.callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]), 
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            Logger.getLogger(LandingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {
                do{
                    System.out.println("--------------------");
                    System.out.println("Event id:");
                    System.out.println(rs.getString("EventId"));
                    System.out.println("Order Date:");
                    System.out.println(rs.getDate("OrderDate"));
                    System.out.println("Tax amount:");
                    System.out.println(rs.getDouble("Tax"));
                    System.out.println("Event name:");
                    System.out.println(rs.getString("EventName"));
                    System.out.println("Start date:");
                    System.out.println(rs.getDate("StartDate"));
                    System.out.println("Seat count in purchase:");
                    System.out.println(rs.getInt("SeatCount"));                
                } while (rs.next());
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
