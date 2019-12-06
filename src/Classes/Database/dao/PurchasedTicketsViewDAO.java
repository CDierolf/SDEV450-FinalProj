package Classes.Database.dao;

import Classes.Database.DatabaseInterface;
import Views.PurchasedTicketsView.PurchasedTicketsViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Course: SDEV 450-81 Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: Classes.Database.dao
 * @Date: Nov 12, 2019
 * @Subclass PurchasedTicketsViewDAO Description: methods for user purchased tickets
 */
//Imports
//Begin Subclass LandingViewDAO
public class PurchasedTicketsViewDAO extends DatabaseInterface {

    /**
     * 
     * @param userID
     * @return ResultSet of all of the user's events, given a user id
     */
    public ResultSet getMyEvents(long userID) {
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        ArrayList<String> userValues = new ArrayList<>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<>();
        String Q1 = "{ call [usp_getFutureEventSeatsForUser](?) }";
        userValues.add(Long.toString(userID));
        dataTypes.add("string");
        ResultSet rs = null;
        try {// execute the stored proc
            rs = callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]),
                    dataTypes.toArray(new String[dataTypes.size()]));
        } catch (SQLException ex) {
            Logger.getLogger(PurchasedTicketsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

} //End Subclass LandingViewDAO
