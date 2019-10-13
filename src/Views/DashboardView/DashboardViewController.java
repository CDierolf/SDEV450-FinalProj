/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.DashboardView;

import Classes.APIs.TicketMaster.TicketMasterAPI;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Views.TicketComponent.TicketComponentController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author pis7ftw
 */
public class DashboardViewController implements Initializable {

    @FXML
    private VBox leftVBox;
    @FXML
    private VBox centerVBox;
    @FXML
    private VBox rightVBox;
    @FXML
    private VBox rightMostVBox;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadUI();
        } catch (IOException ex) {
//            Logger.getLogger(AdminDashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // **** THE CONTENTS OF THIS METHOD ARE ONLY A GUIDE FOR IMPLEMENTATION OF THE TICKETCOMPONENT ****
    public void loadUI() throws IOException {

        TicketMasterAPI tma = new TicketMasterAPI();
        List<Events> events = new ArrayList<>();
        events = tma.findEvents("Metallica", "1").getEmbeddedEvents().getEvents();

        
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Views/TicketComponent/TicketComponent.fxml"));
            VBox pane = loader.load();
            pane.setId(Integer.toString(i));
            TicketComponentController tcCtrl = loader.getController();

            tcCtrl.getEvent(events.get(i));

            // Sort the Views
            if (i % 4 == 0) {
                leftVBox.getChildren().add(pane);
                leftVBox.setSpacing(10);
            } else if (i % 4 == 1) {
                centerVBox.getChildren().add(pane);
                centerVBox.setSpacing(10);
            } else if (i % 4 == 2) {
                rightVBox.getChildren().add(pane);
                rightVBox.setSpacing(10);
            } else if (i % 4 == 3) {
                rightMostVBox.getChildren().add(pane);
                rightMostVBox.setSpacing(10);
            }
        }
    }

}
