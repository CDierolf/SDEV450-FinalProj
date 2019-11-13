package Classes.Database.dao;


/** 
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 11, 2019
 * @Subclass VenueDAO Description: 
 */
//Imports
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Database.DatabaseInterface;
import Classes.Utilities.Alerts;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//Begin Subclass VenueDAO

/**
 *
 * @author Tom.Muck
 */
public class VenueDAO extends DatabaseInterface  {

    /**
     *
     * @return
     * @throws SQLException
     */
    public ResultSet getVenue(int venueid) throws SQLException {
        
        // get the venue seat chart
        StringBuilder sb = new StringBuilder();
        ArrayList<String> venueValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        String Q1 = "{ call usp_SeatsSelect(?) }";
        
        init();
        setDebug(this.getDebug());
        venueValues.add(Integer.toString(venueid));
        dataTypes.add("int");
        ResultSet rs = callableStatementRs(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        //Date desEndDateField = null;// initialize for value coming from DB
        //Views.SeatMaps.Venue.Venue theVenue = new Views.SeatMaps.Venue.Venue(venueid);
        /*
        while (rs.next()) {
            desEndDateField = rs.getDate("desEndDate");
        }// END while (rs.next())
        return desEndDateField;
*/
        return rs;
    }
    /* getVenue passed a string event id */
    
    public ResultSet getVenue(Events event) throws SQLException {    
       
        setDebug(this.getDebug());        
        // get the venue seat chart
        StringBuilder sb = new StringBuilder();
        ArrayList<String> venueValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        // we have to check the database to make sure the event exists, if not, 
        venueValues.add(event.getEventID());
        dataTypes.add("string");
         // call a method to add some dummy sales
        init(); // set up the DB properties 
        String Q1 = "{call usp_EventExists(?,?) }";
        int exists = callableStatementReturnInt(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        close();
        if(exists==0) {
            // call a method to create event in db and populate some fake seat sales for the event
            addEvent(event);            
        } 
       
        Q1 = "{ call usp_getSeatsForEvent(?) }";   
        ResultSet rs = callableStatementRs(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        //Date desEndDateField = null;// initialize for value coming from DB
        //Views.SeatMaps.Venue.Venue theVenue = new Views.SeatMaps.Venue.Venue(venueid);
        /*
        while (rs.next()) {
            desEndDateField = rs.getDate("desEndDate");
        }// END while (rs.next())
        return desEndDateField;
*/
        return rs;
    }
 
    public void addEvent(Events event) {
        ArrayList<String> venueValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        
        /*
        @eventid varchar(255),
        @eventname varchar(100) = NULL,
        --@image varbinary(MAX) = NULL,
        @startTime time(7) = NULL,
        @startDate date = NULL,
        @timeTBA bit = NULL,
        @dateTBA bit = NULL,   
        @price money = NULL,
        @info nvarchar(MAX) = NULL
        */
        venueValues.add(event.getEventID());
        dataTypes.add("string");
        venueValues.add(event.getName());
        dataTypes.add("string");
        if(event.getEventDates().getEventStartData().getEventLocalTime()=="TBD"){ 
            Alerts.genericAlert("Date error","Date error","Date and time is not set for this show yet. Try again at a later date.").showAndWait();   
            venueValues.add("null");
            dataTypes.add("string"); // passing time as string and convert in the stored procedure
            venueValues.add("null");
            dataTypes.add("date");
            return;
        }else{
            venueValues.add(event.getEventDates().getEventStartData().getEventLocalTime());
            dataTypes.add("string"); // passing time as string and convert in the stored procedure
            venueValues.add(event.getEventDates().getEventStartData().getEventLocalDate());
            dataTypes.add("date");           
            
        }

        venueValues.add("0");// timetba
        dataTypes.add("bit");
        venueValues.add("0");// datetba
        dataTypes.add("bit");
        if(event.getPrice()=="TBD"){            
            Alerts.genericAlert("Price error","Price error","Price is not set for this show yet. Try again at a later date.").showAndWait();   
            venueValues.add("0");
            return;
        } else {
            venueValues.add(String.valueOf(event.getPrice()));
        }
        dataTypes.add("string"); // pass as string and convert in database
        venueValues.add("info for event");
        dataTypes.add("string");
        init(); // set up the DB properties 
        // insert the event into the database
       // String Q1 = "{call usp_EventsInsert(?,?,?,?,?,?,?,?)}";
        String Q1 = "INSERT INTO [dbo].[Events] (eventid, [eventname],  [startTime], [startDate], [timeTBA], [dateTBA],  [price], [info])\n" +
"		VALUES (?,?,?,?,?,?,?,?)";/**/
        preparedStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        close();
       
        // generate some sample sales
        venueValues = new ArrayList<String>(); // reset the arrays, only sending event id
        dataTypes = new ArrayList<String>(); 
        venueValues.add(event.getEventID());
        dataTypes.add("string");
        venueValues.add(event.getVenueData().getVenues().get(0).getVenueName());
        dataTypes.add("string");
        Q1 = "{ call [usp_EventsGenerateDummySalesNew](?,?) }";
        preparedStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
        dataTypes.toArray(new String[dataTypes.size()]));
        close();//close connection, statement, resultset
    }

        
} //End Subclass VenueDAO