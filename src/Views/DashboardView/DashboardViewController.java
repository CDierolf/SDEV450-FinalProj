/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.DashboardView;
import Classes.Database.User;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.FindEventsView.FindEventsViewController;
import Views.SeatSelectionView.SeatSelectionViewController;
import Views.LandingView.LandingViewController;
import Views.PurchasedTicketsView.PurchasedTicketsViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Christopher Dierolf
 * @Description: The DashboardViewController is passed around to each page that
 * is loaded into the dynamicViewPane. The DashboardViewController controls all
 * loading, unloading and visibility of items in the dynamicViewPane.
 *
 */
public class DashboardViewController implements Initializable {

    @FXML
    private AnchorPane dynamicViewPane;
    @FXML
    private AnchorPane seatSelectionViewPane;
    @FXML
    private AnchorPane purchasedTicketsViewPane;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws SQLException {
        this.user = user;
        
        //load LandingView here because it needs a user
        try {            
            loadLandingView();                    
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //setUser(new User());
    }

    /**
     * Load LandingView
     *
     * @throws IOException
     */
    public void loadLandingView() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/LandingView/LandingView.fxml"));
        AnchorPane landingViewPane = loader.load();

        LandingViewController landingViewController = loader.getController();
        landingViewController.setDashboardController(this);
        dynamicViewPane.getChildren().clear();
        dynamicViewPane.getChildren().add(landingViewPane);

        
    }
    
    public void loadPurchasedTicketsView() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/PurchasedTicketsView/PurchasedTicketsView.fxml"));
        this.purchasedTicketsViewPane = loader.load();
        
        
        PurchasedTicketsViewController pTicketsViewController = new PurchasedTicketsViewController();
        pTicketsViewController = loader.getController();
        pTicketsViewController.setDashboardController(this);
        dynamicViewPane.getChildren().clear();
        dynamicViewPane.getChildren().add(purchasedTicketsViewPane);
    }
    public void unloadPurchasedTicketsView() {
        dynamicViewPane.getChildren().remove(purchasedTicketsViewPane);
    }

    // Load the FindEventsView into the dynamicViewPane
    // Pass this instance of the DashboardViewController into the
    // FindEventsViewController.
    public void loadFindEventsView() throws IOException {
        //System.out.println("USERNAME:"+this.getUser().getUsername());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/FindEventsView/FindEventsView.fxml"));
        AnchorPane eventsViewPane = loader.load();

        FindEventsViewController eventsViewController = loader.getController();
        eventsViewController.setDashboardController(this);
        dynamicViewPane.getChildren().clear();
        dynamicViewPane.getChildren().add(eventsViewPane);
        
    }

    // Unloads the SeatSelectionView from the dynamicViewPane
    // Does not hide it. Completely removes it. No reference to 
    // SeatSelectionView is maintained.
    public void unloadSeatSelectionView() {
        dynamicViewPane.getChildren().remove(seatSelectionViewPane);
    }

    // Load the SeatSelectionView into dynamicViewPane, 
    // pass the selected event data into its controller
    // along with a reference to this DashboardViewController
    public void loadSeatSelectionView(Events e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/SeatSelectionView/SeatSelectionView.fxml"));
        this.seatSelectionViewPane = loader.load();

        SeatSelectionViewController seatSelectionViewController = loader.getController();
        seatSelectionViewController.setDashboardController(this);
        //seatSelectionViewController.setAlert(a);
        seatSelectionViewController.setEventData(e);
        toggleEventViewVisiblity(true);

        dynamicViewPane.getChildren().add(seatSelectionViewPane);
    }

    // Toggles the visiblity of the FindEventsView instance. 
    // FindEventsView is hidden so that if the user backs out of SeatSelectionView
    // The events the user searched for are readily available for continued browsing.
    public void toggleEventViewVisiblity(boolean isVisible) {
        dynamicViewPane.getChildren().get(0).setVisible(isVisible);
    }
}
