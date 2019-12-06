package Classes.Database.dao;


/** 
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 11, 2019
 * @Subclass VenueDAO Description: 
 * venue object for seating for an event
 */
//Imports
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Database.DatabaseInterface;
import Classes.Utilities.Alerts;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    /**
     * 
     * @param venueid
     * @return
     * @throws SQLException 
     * gets a venue given a venue id, not used in the app
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

        return rs;
    }

    
    /**
     * 
     * @param event
     * @return
     * @throws SQLException 
     * gets a venue given an event -- returns a resultset with all seats and seat
     * status (sold/unsold)
     */
    public ResultSet getVenue(Events event) throws SQLException {    
       
        setDebug(this.getDebug());        
        // get the venue seat chart
        StringBuilder sb = new StringBuilder();
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        ArrayList<String> venueValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 

        venueValues.add(event.getEventID());
        dataTypes.add("string");

        init(); // set up the DB properties 
        String Q1 = "{call usp_EventExists(?,?) }";
        // check to see if the event exists
        int exists = callableStatementReturnInt(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        close();
        if(exists==0) {
            // event doesn't exist, add it
            addEvent(event);            
        } 
       
        Q1 = "{ call usp_getSeatsForEvent(?) }";   
        ResultSet rs = callableStatementRs(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));

        return rs;
    }
 
    /**
     * 
     * @param event 
     * Adds the event to the database, given an event object
     */
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
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
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
           // Alerts.genericAlert("Price error","Price error","Price is not set for this show yet. Try again at a later date.").showAndWait();   
            venueValues.add("0");
            return;
        } else {
            venueValues.add(String.valueOf(event.getPrice()));
        }
        dataTypes.add("string"); // pass as string and convert in database
        venueValues.add("info for event");
        dataTypes.add("string");
        venueValues.add(event.getImageUrl());
        dataTypes.add("string");
        init(); // set up the DB properties 
        // insert the event into the database
       // String Q1 = "{call usp_EventsInsert(?,?,?,?,?,?,?,?)}";
        String Q1 = "INSERT INTO [dbo].[Events] (eventid, [eventname],  [startTime], [startDate], [timeTBA], [dateTBA],  [price], [info], [image])\n" +
"		VALUES (?,?,?,?,?,?,?,?,?)";/**/
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
        venueValues.add(event.getVenueData().getVenues().get(0).getVenueCity());
        dataTypes.add("string");
        venueValues.add(event.getVenueData().getVenues().get(0).getVenueState());
        dataTypes.add("string");

        // add some dummy sales if the event doesn't exist in the db.
        // This is for the example app that shows
        //  a partially filled venue for demonstration purposes
        Q1 = "{ call [usp_EventsGenerateDummySales_1116](?,?,?,?) }";
        preparedStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
        dataTypes.toArray(new String[dataTypes.size()]));
        close();//close connection, statement, resultset
    }

        
} //End Subclass VenueDAO