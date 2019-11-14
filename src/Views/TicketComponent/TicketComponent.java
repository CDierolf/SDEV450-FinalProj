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
import Classes.Utilities.Alerts;
import Classes.Database.Event;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

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

    private Events event; // Event stored for UI interaction
    private DashboardViewController dvc; // To update Dashboard view

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

    }
    
    public void setEventData(Event DBEvent){
        this.eventLabel.setText(DBEvent.getEventName());
        this.dateTimeLabel.setText(DBEvent.getStartDate().toString()); //FIXME format date
        //TODO add venue info
        
        this.actionButton.setText("View Tickets");
    }

    public void setEventData(Events event, FindEventsViewController fevc, DashboardViewController dvc) {

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
        this.event = event;
        this.dvc = dvc;
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
                getImage(event);
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

    public void getImage(Events event) throws FileNotFoundException {
        if (event.getEventImage() != null) {
            eventImageView.setImage(event.getEventImage());
        }
    }

    public void buttonClicked() {
        //if not purchased
        try {
            purchaseTickets();
        } catch (IOException e) {
            System.out.println((e.toString()));
        }

        //if purchased
        try {
            viewTicket();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    // Event handler for "Puchase Tickets" button
    public void purchaseTickets() throws IOException {

        //        
        //        String date = event.getEventDates().getEventStartData().getEventLocalDate();
        //        String time = event.getEventDates().getEventStartData().getEventLocalTime();
        //        System.out.println(event.getName() + " " + date + time);
        //        System.out.println(event);
        //
        // Load the SeatSelectionView
        dvc.loadSeatSelectionView(event);
        // Hide the FindEventsView
        dvc.toggleEventViewVisiblity(false);
    }

    public void viewTicket() throws IOException {
        //TODO add logic to show ticket for this event
    }

}
