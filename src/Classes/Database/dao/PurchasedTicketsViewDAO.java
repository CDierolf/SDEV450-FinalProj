package Classes.Database.dao;

import Classes.Database.DatabaseInterface;
import Views.PurchasedTicketsView.PurchasedTicketsViewController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: Classes.Database.dao
 * @Date: Nov 12, 2019
 * @Subclass LandingViewDAO Description: 
 */
//Imports

//Begin Subclass LandingViewDAO
public class PurchasedTicketsViewDAO  extends DatabaseInterface  {
    public ResultSet getMyEvents(long userID) {
        ArrayList<String> userValues = new ArrayList<>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<>();
        String Q1 = "{ call [usp_getAllEventSeatsForUser](?) }";
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