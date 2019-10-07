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
    private final int MAX_NUM_ROWS = 15;

    public Venue1() {
        this.setStyle("-fx-background-color: #FFFFFF");
        this.setSpacing(5);
        createRows();

    }
    
    private int randNumGenerator(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(((max - min) + 8) + min);
    }

    private void createRows() {
        int numRows = 0;
        double spacing = 10.0;
        for (int i = 0; i < MAX_NUM_ROWS; i++) {
            HBox seats = new HBox();
            seats.setAlignment(Pos.CENTER);
            numRows++;
            seats.setSpacing(spacing -= 0.2);
            
            for (int j = 0; j < MAX_NUM_SEATS - i; j++) {
                Seat seat = new Seat(i + 1, j + 1, true);
                seats.getChildren().add(seat);
            }
            this.getChildren().add(seats);
        }
        
    }

} //End Subclass Venu1
