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
public class UserDAO extends DatabaseInterface  {
    public ResultSet getUser(int userid) throws SQLException {
        init();
        // get the user
        StringBuilder sb = new StringBuilder();
        ArrayList<String> userValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        String Q1 = "{ call [usp_UsersSelect](?) }";
        
        init();
        setDebug(this.getDebug());
        userValues.add(Integer.toString(userid));
        dataTypes.add("int");
        ResultSet rs = callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        
        /*
        while (rs.next()) {
            desEndDateField = rs.getDate("desEndDate");
        }// END while (rs.next())
        
*/
        return rs;
    }
    
    
    
    public void addUser(String username, String password, String email) {
        init();
        ArrayList<String> userValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        
        userValues.add(username);
        dataTypes.add("string");
        userValues.add(password);
        dataTypes.add("string");
        userValues.add(email);
        dataTypes.add("string");
        // insert the event into the database
        String Q1 = "{call [usp_UsersInsert](?,?,?,?)}";
        int userid = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        
        close();//close connection, statement, resultset
        return;
    }
    
    public int loginUser(String username, String password) {
        init();
        String Q1 = "{call usp_loginUser(?,?,?) }";
        ArrayList<String> userValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        
        userValues.add(username);
        dataTypes.add("string");
        userValues.add(password);
        dataTypes.add("string");
        int loggedin = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        if(loggedin==0) {
            // login failed    
            return 0;
        } else {
            // login succeeded
            return 1;
        }
    }
    
    
    
    
    
    
    
} //End Subclass UserDAO