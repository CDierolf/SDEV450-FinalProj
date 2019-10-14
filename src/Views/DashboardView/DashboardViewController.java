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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;

    List<Events> events = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Not implemented.
    }

    private Image downloadImage(Events event) throws InterruptedException, ExecutionException {
        Image image;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(() -> {
            Image image1;
            image1 = new Image(event.getImageUrl());
            return image1;
        });

        image = (Image) future.get();
        executorService.shutdown();
        return image;
    }

    public void loadEvents(String eventKeyword, String pageNumber) {

        TicketMasterAPI tma = new TicketMasterAPI();

        events = tma.findEvents(eventKeyword, pageNumber).getEmbeddedEvents().getEvents();

        for (int i = 0; i < events.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/Views/TicketComponent/TicketComponent.fxml"));
                VBox pane = loader.load();
                pane.setId(Integer.toString(i));
                TicketComponentController tcCtrl = loader.getController();

                try {
                    tcCtrl.getEvent(events.get(i));
                } catch (ExecutionException ex) {
                    Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

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

            } catch (IOException ex) {
                Logger.getLogger(DashboardViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void getEvents() {
        clearEvents();
        loadEvents(this.searchTextField.getText(), "1");
    }

    private void clearEvents() {
        this.events.clear();
        leftVBox.getChildren().clear();
        centerVBox.getChildren().clear();
        rightVBox.getChildren().clear();
        rightMostVBox.getChildren().clear();
    }
}
