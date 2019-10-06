package Views.SeatMaps.Venue1;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Views.SeatMaps.Venue1
 * @Date: Oct 6, 2019
 * @Subclass Venue1 Description:
 */
//Imports
//Begin Subclass Venu1
public class Venue1 extends VBox {

    VBox rows = new VBox();
    private final int MAX_NUM_SEATS = 30;
    private final int MIN_NUM_SEATS = 12;

    public Venue1() {
        this.setStyle("-fx-background-color: #FFFFFF");
        this.setSpacing(5);
        createRows();

    }
    
    private int randNumGenerator(int maxNumSeats) {
        Random rand = new Random();
        return rand.nextInt(((MAX_NUM_SEATS - maxNumSeats)- MIN_NUM_SEATS) + MIN_NUM_SEATS);
    }

    private void createRows() {
        int numRows = 0;
        for (int i = 0; i < 10; i++) {
            HBox seats = new HBox();
            seats.setAlignment(Pos.CENTER);
            numRows++;
            seats.setSpacing(10);
            for (int j = 0; j < randNumGenerator(numRows); j++) {
                Circle seat = new Circle();
                seat.setFill(Color.DODGERBLUE);
                seat.setRadius(12);
                seats.getChildren().add(seat);
            }
            this.getChildren().add(seats);
        }
        
    }

} //End Subclass Venu1
