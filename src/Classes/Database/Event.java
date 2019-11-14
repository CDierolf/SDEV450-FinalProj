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

//Begin Subclass Event
public class Event {

    String eventID;
    String eventName;
    String imageURL;
    Date startDate;
    double price;
    int venueID;

    DatabaseInterface di = new DatabaseInterface();

    /**
     * Create event object based on eventID
     *
     * @param eventID
     */
    public Event(String eventID) {
        di.init();
        ResultSet rs = di.retrieveRS("SELECT * FROM Events WHERE eventid = '" + eventID + "'");
        this.eventID = eventID;

        try {
            while (rs.next()) {
                eventName = rs.getString("eventname");
                //imageURL=rs.getString("image"); //currently all NULL
                startDate = rs.getDate("startDate");
                Time time = rs.getTime("startTime");
                //TODO add time to startDate
                price = rs.getDouble("price");
                venueID = rs.getInt("venueid");
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        di.close();
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVenueID() {
        return venueID;
    }

    public void setVenueID(int venueID) {
        this.venueID = venueID;
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
