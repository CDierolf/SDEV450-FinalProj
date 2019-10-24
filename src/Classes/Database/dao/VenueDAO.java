package Classes.Database.dao;


/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: com.nc4.factsFileParser.dao
 * @Date: Oct 31, 2018
 * @Subclass EventDAO Description: 
 */
//Imports
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Database.DatabaseInterface;
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

        venueValues.add(event.getEventDates().getEventStartData().getEventLocalTime());
        dataTypes.add("string"); // passing time as string and convert in the stored procedure
        venueValues.add(event.getEventDates().getEventStartData().getEventLocalDate());
        dataTypes.add("date");
        venueValues.add("0");// timetba
        dataTypes.add("bit");
        venueValues.add("0");// datetba
        dataTypes.add("bit");
        venueValues.add(String.valueOf(event.getPrice()));
        dataTypes.add("string"); // pass as string and convert in database
        venueValues.add("info for event");
        dataTypes.add("string");
        init(); // set up the DB properties 
        // insert the event into the database
        String Q1 = "{call usp_EventsInsert(?,?,?,?,?,?,?,?)}";
        callableStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        
        close();//close connection, statement, resultset
        return;
        // generate some sample sales
        /*venueValues = new ArrayList<String>(); // reset the arrays, only sending event id
        dataTypes = new ArrayList<String>(); 
        venueValues.add(event.getEventID());
        dataTypes.add("string");
        Q1 = "{ call [usp_EventsGenerateDummySales](?) }";
        rs = callableStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
        dataTypes.toArray(new String[dataTypes.size()]));
        close();//close connection, statement, resultset*/
    }

        
} //End Subclass VenueDAO