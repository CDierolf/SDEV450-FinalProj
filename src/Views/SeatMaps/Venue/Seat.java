package Views.SeatMaps.Venue;

//Imports
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Neil Hart
 * @Assignment Name: SDEV 450 - Final Project
 * @Date: Oct 6, 2019
 * @Subclass Seat Description: Represents the seats in venue view
 */

//Begin Subclass Seat
public class Seat extends Circle {
    
    private final int CIRCLE_RADIUS = 6;
    private final Color COLOR_AVAILABLE = Color.DODGERBLUE;
    private final Color COLOR_UNAVAILABLE = Color.RED;
    private final Color COLOR_SELECTED = Color.LIME;
    
    // Instance variables
    private final int seatNumber; // Final ivar - set in constructor
    private final int rowNumber; // Final ivar - set in constructor
    private final char section;
    private boolean isAvailable;
    private boolean isSelected;
    
    private final Tooltip tt = new Tooltip("Select this seat");
    
    // Getter methods
    // No setters should be required as set in constructor
    public int getSeatNumber() {
        return seatNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }
    
    // Constructor
    public Seat(int seatNumber, int rowNumber, char section, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.section = section;
        this.isAvailable = isAvailable;
        Tooltip.install(this, tt);
        // Create circle on initialization
        this.setSeatImage();
        // Register with Event Handler
        addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
    
    // Mouse Click Event Handler
    EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("Seat Number: " + seatNumber + ". Row Number: " + rowNumber + ""
                    + " Section: " + section);
            //isAvailable = !isAvailable;
            if (isAvailable) {
                isSelected = !isSelected;
            } else {
                System.out.println("Seat not available.");
            }
            updateImage();
        }
    };  
    
    // Create circle representation of seat for Venue view
    private void setSeatImage() {
        setRadius(CIRCLE_RADIUS);
        // Color seat image based on availability
        updateImage();
    }
    
    // Update circle image color based on availability.
    //
     private void updateImage() {

        if (isAvailable) {
            setFill(COLOR_AVAILABLE);
            tt.setText("Select this seat");
        }
        if (isSelected) {
            setFill(COLOR_SELECTED);
            tt.setText("Selected");
        }
        if (!isAvailable) {
            setFill(COLOR_UNAVAILABLE);
            tt.setText("Seat unavailable");
        }
    }
    
    // Sell seat - change availability and display
    public void sellSeat() {
        isAvailable = false;
        updateImage();
    }

} //End Subclass Seat