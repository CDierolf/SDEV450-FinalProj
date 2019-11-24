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
import Views.DashboardView.DashboardViewController;
import Views.FindEventsView.FindEventsViewController;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
    private Event DBEvent; //Event from database
    private DashboardViewController dvc; // To update Dashboard view
    private boolean purchased;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setEventData(Event DBEvent, DashboardViewController dvc) {
        this.DBEvent = DBEvent;
        purchased = true; //this event has purchases from user
        this.eventLabel.setText(DBEvent.getEventName());
        this.dateTimeLabel.setText(getEventDateTimeDetails(DBEvent)); //FIXME format date
        if (DBEvent.getPrice() == 0) {
            this.pricePerTicketLabel.setText("TBD");
        } else {
            String s = "$" + String.format("%.2f", DBEvent.getPrice());
            this.pricePerTicketLabel.setText(s);
        }
        this.venueLocationLabel.setText(DBEvent.getVenueName());
        this.venueCityStateLabel.setText((DBEvent.getVenueCity() + ", " + DBEvent.getVenueState()));

        //change button label to indicate this is a purchased event
        this.actionButton.setText("View Tickets");
        loadImage();
    }

    public void setEventData(Events event, FindEventsViewController fevc, DashboardViewController dvc) {
        purchased = false; //this event has no purchases from user

        String city = event.getVenueData().getVenues().get(0).getVenueCity();
        String state = event.getVenueData().getVenues().get(0).getVenueState();

        this.eventLabel.setText(event.getName());
        this.dateTimeLabel.setText(getEventDateTimeDetails(event));

        String seatPrice;
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

    private String getEventDateTimeDetails(Event DBEvent) {
        String date = DBEvent.getStartDate().toString();
        String time = DBEvent.getStartTime().toString();

        return date + " " + time;
    }

    // Load images in the background and display when available.
    public void loadImage() {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // call getImages asynchronously
                if (!purchased) {
                    getImage(APIEvent);
                } else {
                    getImage(DBEvent);
                }
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

    public void getImage(Event DBEvent) throws FileNotFoundException {
        if (DBEvent.getImage() != null) {
            eventImageView.setImage(DBEvent.getImage());
        }
    }

    public void buttonClicked() {
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
    public void purchaseTickets() throws IOException {

        
        // TODO Call dvc.openDetailsView(PurchasedEvent pEvent) to load
        // the details view
        //dvc.loadSea(APIEvent);

        // Hide the FindEventsView
        dvc.toggleEventViewVisiblity(false);
    }

    public void viewTicket() throws IOException {
        //TODO add logic to show ticket for this event
    }

}
