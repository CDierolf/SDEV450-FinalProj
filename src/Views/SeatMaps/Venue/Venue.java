package Views.SeatMaps.Venue;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Utilities.Debug;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Venue extends BorderPane implements Debug  {

   

    public Venue(int venueId) {
        try {
            this.setStyle("-fx-background-color: #FFFFFF");
            Classes.Database.dao.VenueDAO dao = new Classes.Database.dao.VenueDAO();
            ResultSet rs = dao.getVenue(venueId);
            /* display seats for debugging
            while (rs.next()) {
                System.out.println("Row" + rs.getString("row") +
                        " , Seat:" + rs.getString("seat") + ", Section:" +  rs.getString("section"));
            }
            */
            //setTop(createRows(14,27)); // hard coded rows/seats
            setTop(createRows(rs));
            setBottom(addStage());
            setCenter(moshPit());
            
        } catch (SQLException ex) {
            Logger.getLogger(Venue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /* get the venue from the event id.
    if the event does not exist in the database, add it and populate some sample sales 
    */
    public Venue(Events event) {
        try {
            this.setStyle("-fx-background-color: #FFFFFF");
            Classes.Database.dao.VenueDAO dao = new Classes.Database.dao.VenueDAO();
            ResultSet rs = dao.getVenue(event);
            /* display seats for debugging*/
            /*while (rs.next()) {
                System.out.println("Row" + rs.getString("row") + " , Seat:" + rs.getString("seat") + 
                        ", Section:" +  rs.getString("section") + "SOLD? " + rs.getInt("sold"));
            }*/
            
            //setTop(createRows(14,27)); // hard coded rows/seats
            setTop(createRows(rs));
            setBottom(addStage());
            //setCenter(moshPit()); // mosh pit was hard coded, will have to set it up in DB if we want to sell tickets
            
        } catch (SQLException ex) {
            Logger.getLogger(Venue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private VBox createRows(int rowcount, int seatsPerRow) {
        VBox rows = new VBox();
        rows.setSpacing(5);
        rows.setAlignment(Pos.CENTER);
        //int numRows = 0;
        double spacing = 10.0;
        for (int i = 0; i < rowcount; i++) {
            HBox seats = new HBox();
            seats.setAlignment(Pos.CENTER);
            //numRows++;
            seats.setSpacing(spacing -= 0.2);
            
            for (int j = 0; j < seatsPerRow; j++) {
                Seat seat = new Seat(i + 1, j + 1, 'G', true);
                seats.getChildren().add(seat);
            }
            rows.getChildren().add(seats);
        }
        
        return rows;
        
    }
    
    // create the display from the database
    private VBox createRows(ResultSet rs) throws SQLException {
        VBox rows = new VBox();
        rows.setSpacing(5);
        rows.setAlignment(Pos.CENTER);
        double spacing = 10.0;
        rs.next(); // get first row
        String row = rs.getString("row");
        String lastrow = rs.getString("row");
        int i=0;int j=0;
        HBox seats = new HBox();
        Boolean available = (rs.getInt("sold")!=1);
        do { 
            row = rs.getString("row");
            available = (rs.getInt("sold")!=1);
            if(row.equals(lastrow)) {                
//            
//                System.out.println("Row" + rs.getString("row") +
//                       " , Seat:" + rs.getString("seat") + ", Section:" +  rs.getString("section"));          

                seats.setAlignment(Pos.CENTER);

                seats.setSpacing(spacing);            
                Seat seat = new Seat(i++, j, 'G', available);
                seats.getChildren().add(seat);
                //lastrow = row;
            }else{
//                System.out.println("NewRow" + rs.getString("row") +
//                       " , Seat:" + rs.getString("seat") + ", Section:" +  rs.getString("section"));          

                rows.getChildren().add(seats);
                lastrow = row;
                j++;
                i=0;
                seats = new HBox();
            }
        }while (rs.next());
        
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
                Seat seat = new Seat(i+1, j+1, 'M', true);
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
    /**
     *
     * @param theArray
     * @return
     */
    @Override
     public String myToString(String[] theArray) {
        String result = "[";
        for (int i = 0; i < theArray.length; i++) {
           if (i > 0) {
              result = result + ",";
           }
           String item = theArray[i];
           result = result + item;
        }
        result = result + "]";
        return result;
     }

    @Override
    public boolean getLogToFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLogToFile(boolean logToFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logDebug(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logDebug(String message, String level) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logDebug(String message, String level, boolean robust) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDebug(boolean debug) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getDebug() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
} //End Subclass Venu1
