/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PurchasedTicketsView;

import Classes.Database.DatabaseInterface;
import Classes.Database.PurchasedEvent;
import Views.DashboardView.DashboardViewController;
import Views.FindEventsView.FindEventsViewController;
import Views.LandingView.LandingViewController;
import Views.TicketComponent.TicketComponentController;
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
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class PurchasedTicketsViewController implements Initializable {

    @FXML
    private VBox root;

    DashboardViewController dvc;
    List<TicketComponentController> tcElements = new ArrayList<>();
    List<VBox> ticketComponents = new ArrayList<>();
    DatabaseInterface di = new DatabaseInterface();
    ArrayList<PurchasedEvent> eventData = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void loadTicketComponents() {
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TicketComponent/PurchasedTicketViewComponent.fxml"));
                VBox ticketComponentPane = loader.load();

                ticketComponentPane.setId(Integer.toString(i));

                ticketComponents.add(ticketComponentPane);
                TicketComponentController tcCtrl = loader.getController();
                tcElements.add(tcCtrl);

            } catch (IOException ex) {
                Logger.getLogger(FindEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void loadEventDataToComponents() throws FileNotFoundException {

        for (int i = 0; i < ticketComponents.size(); i++) {
            //tcElements.get(i).setEventData(events.get(i), PurchasedTicketsViewController.this, dvc);
            //tcElements.get(i).loadImage(); now called inside TicketComponent
            displayEventComponents(ticketComponents.get(i), i);
        }
    }

    private void displayEventComponents(VBox pane, int i) {
        root.getChildren().add(pane);
        root.setSpacing(20);

    }

    private void getEventData() {
        di.init();
        ArrayList<String> userValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>();
        String Q1 = "{ call [usp_UsersEventsSelect](?) }";
        userValues.add(Long.toString(dvc.getUser().getUserID()));
        dataTypes.add("int");
        ResultSet rs = null;
        try {// execute the stored proc
            rs = di.callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            Logger.getLogger(LandingViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {
                do {
                    PurchasedEvent purchasedEvent = new PurchasedEvent();
                    purchasedEvent.setEventName(rs.getString("EventName"));
                    purchasedEvent.setEventDate(rs.getDate("StartDate"));
                    this.eventData.add(purchasedEvent);

                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
