package Views.TicketComponent;

/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Stephen Graybeal, based heavily on Chris's
 * TicketComponentController
 * @Assignment Name: Views.TicketComponent
 * @Date: Nov 3, 2019
 * @Subclass TicketComponent Description:
 */
//Imports
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Objects.Event;
import Classes.Objects.PurchasedEvent;
import Classes.Utilities.Alerts;
import Classes.Utilities.Enums.ViewEnum;
import Views.DashboardView.DashboardViewController;
import Views.FindEventsView.FindEventsViewController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Tooltip;

//Begin Subclass TicketComponent
public class TicketComponent implements Initializable {

    @FXML
    private Label eventLabel;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Label venueCityStateLabel;
    @FXML
    private Label venueLocationLabel;
    @FXML
    private Label pricePerTicketLabel;
    @FXML
    private Button actionButton;
    @FXML
    private ImageView eventImageView;
    @FXML
    private Image eventImage;
    @FXML
    private VBox imageVBox;

    private Events APIEvent; // Event stored for UI interaction
    private PurchasedEvent pEvent;
    private DashboardViewController dvc; // To update Dashboard view
    private boolean purchased;
    private ViewEnum view;
    private String seatPrice = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setView(ViewEnum view) {
        this.view = view;
    }

    public void setEventData(PurchasedEvent pEvent, DashboardViewController dvc) {
        this.pEvent = pEvent;
        this.dvc = dvc;
        this.purchased = true;
        this.eventLabel.setText(pEvent.getEventName());
        //add tooltip for overflowing event names
        Tooltip.install(eventLabel, new Tooltip(pEvent.getEventName()));

        this.dateTimeLabel.setText(String.format("%s %s", pEvent.getEventDate(), pEvent.getEventTime())); //FIXME format date
        if (pEvent.getEventPrice() == 0) {
            this.pricePerTicketLabel.setText("TBD");
        } else {
            String s = "$" + String.format("%.2f", pEvent.getEventPrice());
            this.pricePerTicketLabel.setText(s);
        }
        this.venueLocationLabel.setText(pEvent.getVenue().getVenueName());
        this.venueCityStateLabel.setText((pEvent.getVenue().getVenueCity() + ", " + pEvent.getVenue().getVenueState()));

        //change button label to indicate this is a purchased event
        this.actionButton.setText("View Tickets");
        loadImage(pEvent.getEventImageUrl());
    }

    public void setEventData(Events event, FindEventsViewController fevc, DashboardViewController dvc) {

        purchased = false; //this event has no purchases from user

        String city = event.getVenueData().getVenues().get(0).getVenueCity();
        String state = event.getVenueData().getVenues().get(0).getVenueState();

        this.eventLabel.setText(event.getName());
        //add tooltip for overflowing event names
        Tooltip.install(eventLabel, new Tooltip(event.getName()));

        this.dateTimeLabel.setText(getEventDateTimeDetails(event));

        if (!"TBD".equals(event.getPrice())) {
            double pricePerTicketValue = Double.valueOf(event.getPrice());
            seatPrice = String.format("%.2f", pricePerTicketValue);
        } else {
            seatPrice = event.getPrice();
        }
        this.pricePerTicketLabel.setText("$" + seatPrice);

        this.venueLocationLabel.setText(event.getVenueData().getVenues().get(0).getVenueName());
        this.venueCityStateLabel.setText(city + ", " + state);

        // Set event and dashboard variables
        this.APIEvent = event;
        this.dvc = dvc;

        //load images
        loadImage();
    }

    private String getEventDateTimeDetails(Events event) {
        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        String time = event.getEventDates().getEventStartData().getEventLocalTime();

        return date + " " + time;
    }

    // Load images in the background and display when available.
    public void loadImage() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // call getImages asynchronously
                getImage(APIEvent);

                return null;
            }
        };

        task.setOnSucceeded((WorkerStateEvent event1) -> {
            Void result = task.getValue();
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    // For purchasedticketsviewcomponentcontroller image by pEvent url
    public void loadImage(String url) {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // call getImages asynchronously
                getImage(url);
                return null;
            }
        };

        task.setOnSucceeded((WorkerStateEvent event1) -> {
            Void result = task.getValue();
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public void getImage(String url) {
        if (url != null) {
            eventImageView.setImage(new Image(url));
        }
    }

    public void getImage(Events event) throws FileNotFoundException {
        if (event.getEventImage() != null) {
            eventImageView.setImage(event.getEventImage());
        }
    }

    public void buttonClicked() throws NoSuchAlgorithmException, SQLException {
        System.out.println(purchased);
        if (!purchased) {
            try {
                purchaseTickets();
            } catch (IOException e) {
                System.out.println((e.toString()));
            }
        }

        if (purchased) {
            try {
                viewTicket();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    // Event handler for "Puchase Tickets" button
    public void purchaseTickets() throws IOException, NoSuchAlgorithmException, SQLException {

        if (seatPrice == "TBD") {
            Alerts.genericAlert("Price error", "Price error", "Price is not set for this show yet. Try again at a later date.").showAndWait();
        } else {
            dvc.loadSeatSelectionView(APIEvent, this.view);
            // Hide the FindEventsView
            dvc.toggleEventViewVisiblity(false);
            dvc.unloadLandingView();
        }
    }

    public void viewTicket() throws IOException, NoSuchAlgorithmException, SQLException {

        dvc.openDetailsView(pEvent);
    }

}
