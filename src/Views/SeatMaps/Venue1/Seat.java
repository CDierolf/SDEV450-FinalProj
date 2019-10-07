package Views.SeatMaps.Venue1;

//Imports
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Neil Hart
 * @Assignment Name: SDEV 450 - Final Project
 * @Date: Oct 6, 2019
 * @Subclass Seat Description: Represents the seats in venue view
 */

//Begin Subclass Seat
public class Seat {
    
    // Instance variables
    private final int seatNumber; // Final ivar - set in constructor
    private final int rowNumber; // Final ivar - set in constructor
    private Circle seatImage;
    private boolean isAvailable;
    
    // Getter methods
    // No setters should be required as set in constructor
    public int getSeatNumber() {
        return seatNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }
    
    // Constructor
    public Seat(int seatNumber, int rowNumber, boolean isAvailable) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.isAvailable = isAvailable;
        
        // Create circle on initialization
        this.createSeatImage();
    }
    
    // Create circle representation of seat for Venue view
    private void createSeatImage() {
        Circle seatImg = new Circle();
        this.seatImage = seatImg;
        // Color seat image based on availability
        updateImage();
    }
    
    // Update circle image color based on availability
    private void updateImage() {
        if (isAvailable) {
            seatImage.setFill(Color.DODGERBLUE);
        } else {
            seatImage.setFill(Color.RED);
        }
    }
    
    // Sell seat - change availability and display
    public void sellSeat() {
        isAvailable = false;
        updateImage();
    }

} //End Subclass Seat