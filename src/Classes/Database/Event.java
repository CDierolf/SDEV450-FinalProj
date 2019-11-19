package Classes.Database;

/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Classes.Database
 * @Date: Oct 12, 2019
 * @Subclass Event Description:
 */
//Imports
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import Classes.Database.dao.EventDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import Classes.APIs.TicketMaster.*;
import javafx.scene.image.Image;

//Begin Subclass Event
public class Event {

    private String eventID;
    private String eventName;
    private Image image;
    private Date startDate;
    private Date startTime;
    private double price;
    private int venueID;
    private String venueName;
    private String venueCity;
    private String venueState;

    DatabaseInterface di = new DatabaseInterface();

    /**
     * Create event object based on eventID
     *
     * @param eventID
     */
    public Event(String eventID) {
        //load what we can from the database
        Classes.Database.dao.EventDAO dao = new Classes.Database.dao.EventDAO();
        dao.init();
        ResultSet rs = null;
        try {
            rs = dao.getEvent(eventID);
        } catch (SQLException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.eventID = eventID;

        try {
            while (rs.next()) {
                eventName = rs.getString("eventname");
                startDate = rs.getDate("startDate");
                startTime = rs.getTime("startTime");
                price = rs.getDouble("price");
                venueID = rs.getInt("venueid"); 
                image = new Image(rs.getString("image"));
                venueName = rs.getString("venueName");
                venueCity = rs.getString("venueCity");
                venueState = rs.getString("venueState");
            }

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            dao.close();
        }

        
    }

     

    public Image getImage() {
        return image;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public String getVenueState() {
        return venueState;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public double getPrice() {
        return price;
    }

    public int getVenueID() {
        return venueID;
    }

    public static Date toDate(String date) {
        Date myDate = new Date();
        SimpleDateFormat APIDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try {
            myDate = APIDate.parse(date);
        } catch (Exception e) {
            //time is probably TBD
            APIDate = new SimpleDateFormat("yyyy-mm-dd");
            try {
                myDate = APIDate.parse(date);
                myDate.setHours(14);
                myDate.setMinutes(14);
            } catch (Exception f) {
                System.out.println(f.toString());
            }
        }

        return myDate;
    }

} //End Subclass Event
