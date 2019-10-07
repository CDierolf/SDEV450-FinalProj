package Views.SeatMaps.Venue1;

import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Views.SeatMaps.Venue1
 * @Date: Oct 6, 2019
 * @Subclass Venue1 Description:
 */
//Imports
//Begin Subclass Venu1
public class Venue1 extends BorderPane {

    private final int MAX_NUM_SEATS = 30;
    private final int MIN_NUM_SEATS = 12;
    private final int MAX_NUM_ROWS = 12;

    public Venue1() {
        this.setStyle("-fx-background-color: #FFFFFF");
        
        setTop(createRows());
        setBottom(addStage());
        setCenter(moshPit());

    }
    
    private int randNumGenerator(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(((max - min) + 8) + min);
    }

    private VBox createRows() {
        VBox rows = new VBox();
        rows.setSpacing(5);
        rows.setAlignment(Pos.CENTER);
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
            rows.getChildren().add(seats);
        }
        
        return rows;
        
    }
    
    private VBox addStage() {
        VBox stageVBox = new VBox();
        stageVBox.setAlignment(Pos.CENTER);
        Rectangle eventStage = new Rectangle();
        Label label = new Label("Stage");
        eventStage.setWidth(500);
        eventStage.setHeight(100);
        eventStage.setFill(Color.GRAY);
        eventStage.setArcWidth(500);
        eventStage.setArcHeight(5);
        stageVBox.getChildren().addAll(label, eventStage);
        return stageVBox;
    }
    
    private HBox moshPit() {
        HBox center = new HBox();
        VBox v = new VBox();
        for (int i = 0; i < 3; i++) {
            HBox h = new HBox();
            for (int j = 0; j < 15; j++) {
                Seat seat = new Seat(i+1, j+1, true);
                h.getChildren().add(seat);
            }
            v.getChildren().add(h);
        }
        v.setAlignment(Pos.CENTER);
        v.setSpacing(3);
        center.getChildren().add(v);
        center.setAlignment(Pos.CENTER);
        return center;
        
    }

} //End Subclass Venu1
