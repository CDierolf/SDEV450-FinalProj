package Views.SeatMaps.Venue1;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Views.SeatMaps.Venue1
 * @Date: Oct 6, 2019
 * @Subclass Seat Description: 
 */
//Imports

//Begin Subclass Seat
public class Seat extends Circle{
    
    private int seatNumber;
    private int rowNumber;
    private boolean isAvailable;
    
    public Circle createSeat() {
        Circle seat = new Circle();
        seat.setFill(Color.DODGERBLUE);
        return seat;
        
        
    }

} //End Subclass Seat