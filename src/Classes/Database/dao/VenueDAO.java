package Classes.Database.dao;


/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: com.nc4.factsFileParser.dao
 * @Date: Oct 31, 2018
 * @Subclass EventDAO Description: 
 */
//Imports
import Classes.Database.DatabaseInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet rs = callableStatement(Q1, venueValues.toArray(new String[venueValues.size()]), 
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
 
 
        
} //End Subclass VenueDAO