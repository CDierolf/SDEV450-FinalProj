package Classes.Database.dao;


/** 
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 10, 2019
 * @Subclass EventDAO Description: 
 */
//Imports

import Classes.Database.DatabaseInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//Begin Subclass EventDAO


public class EventDAO extends DatabaseInterface  {
    /**
     * 
     * @param eventid
     * @return
     * @throws SQLException 
     * Gets an event ResultSet given the eventid
     */
    public ResultSet getEvent(String eventid) throws SQLException {
        init();
        // get the user
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        StringBuilder sb = new StringBuilder();
        ArrayList<String> eventValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        String Q1 = "{call usp_EventsSelect (?)}";
        
        init();
        setDebug(this.getDebug());
        eventValues.add(eventid);
        dataTypes.add("string");
        ResultSet rs = callableStatementRs(Q1, eventValues.toArray(new String[eventValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        

        return rs;
    }
    

    
} //End Subclass EventDAO