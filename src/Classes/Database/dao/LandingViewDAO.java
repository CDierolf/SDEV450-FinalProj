package Classes.Database.dao;

import Classes.Database.DatabaseInterface;
import java.sql.ResultSet;

/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: Classes.Database.dao
 * @Date: Nov 12, 2019
 * @Subclass LandingViewDAO Description: database methods for the landing view
 */
//Imports

//Begin Subclass LandingViewDAO
public class LandingViewDAO  extends DatabaseInterface  {
    /**
     * 
     * @param userID
     * @return a ResultSet of all of the current user's events given a userid
     */
    public ResultSet getMyEvents(long userID) {
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        String[] userValues = {Long.toString(userID)};
        String[] dataTypes = {"string"};

        String Q1 = "SELECT TOP 4 EventId FROM UsersEvents "
                + "WHERE UserId = ? order by OrderDate desc";


        ResultSet rs = preparedStatementRs(Q1,userValues,dataTypes);
        return rs;
    }
} //End Subclass LandingViewDAO