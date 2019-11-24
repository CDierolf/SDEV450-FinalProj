package Classes.Database.dao;


/** 
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 10, 2019
 * @Subclass UserDAO Description: 
 */
//Imports

import Classes.Database.DatabaseInterface;
import Classes.Objects.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Classes.Utilities.PasswordUtilities;
import java.security.NoSuchAlgorithmException;
//Begin Subclass UserDAO

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
    
    public int addUser(User user) throws NoSuchAlgorithmException {
         init();
        ArrayList<String> userValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        
        userValues.add(user.getUsername());
        dataTypes.add("string");
        // Hash the password -- store the hashed password
        PasswordUtilities pu = new PasswordUtilities();        
        userValues.add(pu.getHashedPassword(user.getPassword()));
        dataTypes.add("string");
        userValues.add(user.getEmail());
        dataTypes.add("string");
        // insert the event into the database
        String Q1 = "{call [usp_UsersInsert](?,?,?,?)}"; // sp not working, use insert statement
        int userid = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        userValues.add(Integer.toString(userid));
        dataTypes.add("int");
        /*Q1 = "INSERT INTO [dbo].[Users] ([username], [password], [email],[userID])\n" +
"	VALUES ( ?,?,?,?)";*/
        preparedStatement(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        close();//close connection, statement, resultset
        return userid;
    }
    
    public int addUser(String username, String password, String email) throws NoSuchAlgorithmException {
        init();
        ArrayList<String> userValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        PasswordUtilities pu = new PasswordUtilities();        
        
        userValues.add(username);
        dataTypes.add("string");
        userValues.add(pu.getHashedPassword(password));
        dataTypes.add("string");
        userValues.add(email);
        dataTypes.add("string");
        // insert the event into the database
        String Q1 = "{call [usp_UsersInsert](?,?,?,?)}";
        int userid = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        
        close();//close connection, statement, resultset
        return userid;
    }
    
    public int loginUser(String username, String password) throws NoSuchAlgorithmException {
        init();
        String Q1 = "{call usp_loginUser(?,?,?) }";
        ArrayList<String> userValues = new ArrayList<String>(); 
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        
        userValues.add(username);
        dataTypes.add("string");
        // Hash the password -- compare the hashed password
        PasswordUtilities pu = new PasswordUtilities();        
        userValues.add(pu.getHashedPassword(password));
        dataTypes.add("string");

        int loggedin = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));

            return loggedin;

    }
    
    
    
    
    
    
    
} //End Subclass UserDAO