package Classes.Database.dao;

/**
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 10, 2019
 * @Subclass UserDAO Description: User information from the database
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
public class UserDAO extends DatabaseInterface {

    /**
     *
     * @param userid
     * @return
     * @throws SQLException gets a resultset with user properties given a user
     * id
     */
    public ResultSet getUser(long userid) throws SQLException {
        init();
        // get the user
        StringBuilder sb = new StringBuilder();
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
        ArrayList<String> userValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>();
        String Q1 = "{ call [usp_UsersSelect](?) }";

        init();
        setDebug(this.getDebug());
        userValues.add(Long.toString(userid));
        dataTypes.add("int");
        ResultSet rs = callableStatementRs(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        return rs;
    }

    /**
     *
     * @param user
     * @return long userid
     * @throws NoSuchAlgorithmException Adds a user to the database, given a
     * user object
     */
    public long addUser(User user) throws NoSuchAlgorithmException {
        init();
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
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
        long userid = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));
        userValues.add(Long.toString(userid));
        dataTypes.add("int");
        /*Q1 = "INSERT INTO [dbo].[Users] ([username], [password], [email],[userID])\n" +
"	VALUES ( ?,?,?,?)";*/
        preparedStatement(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));
        close();//close connection, statement, resultset
        return userid;
    }

    /**
     *
     * @param username
     * @param password
     * @param email
     * @return long userid
     * @throws NoSuchAlgorithmException adds a user to the database, given a
     * username/password and email
     */
    public long addUser(String username, String password, String email) throws NoSuchAlgorithmException {
        init();
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
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
        long userid = callableStatementReturnInt(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        close();//close connection, statement, resultset
        return userid;
    }

    /**
     *
     * @param username
     * @param password
     * @return long userid
     * @throws NoSuchAlgorithmException Logs in the user given username/password
     */
    public long loginUser(String username, String password) throws NoSuchAlgorithmException {
        init();
        String Q1 = "{call usp_loginUser(?,?,?) }";
        // We need to set up the parameters for the stored proc into an arraylist
        //  and put the corresponding data type into a corresponding arraylist
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

    /**
     * Updates database with user details ignores userID, username, password,
     * email
     *
     * @param u
     * @return
     */
    public void updateUserDetails(UserInfo u) {
        init();
        String Q1 = "{call [usp_UsersUpdate](?,?,?,?,?,?,?,?,?,?,?) }";
        ArrayList<String> userValues = new ArrayList<String>();
        ArrayList<String> dataTypes = new ArrayList<String>();

        userValues.add(Long.toString(u.userID));
        dataTypes.add("int");
        userValues.add(null); //username
        userValues.add(null); //password
        userValues.add(u.email);
        userValues.add(u.firstname);
        userValues.add(u.lastname);
        userValues.add(u.address1);
        userValues.add(u.address2);
        userValues.add(u.city);
        userValues.add(u.state);
        userValues.add(u.zipcode);
        //add 10 strings
        for (int i = 0; i < 10; i++) {
            dataTypes.add("string");
        }

        callableStatement(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        close();//close connection, statement, resultset

    }

    /**
     * Updates hashed password for user in database
     *
     * @param userID
     */
    public void changePassword(long userID, String password) throws NoSuchAlgorithmException {
        init();
        String Q1 = "{call [usp_UsersUpdate](?,?,?,?,?,?,?,?,?,?,?) }";
        ArrayList<String> userValues = new ArrayList<String>();
        ArrayList<String> dataTypes = new ArrayList<String>();

        userValues.add(Long.toString(userID)); //userID
        dataTypes.add("int");
        userValues.add(null); //username
        PasswordUtilities pu = new PasswordUtilities();
        userValues.add(pu.getHashedPassword(password));
        userValues.add(null); //email
        userValues.add(null); //first
        userValues.add(null); //last
        userValues.add(null); //address1
        userValues.add(null); //address2
        userValues.add(null); //city
        userValues.add(null); //state
        userValues.add(null); //zipcode
        //add 10 strings
        for (int i = 0; i < 10; i++) {
            dataTypes.add("string");
        }

        callableStatement(Q1, userValues.toArray(new String[userValues.size()]),
                dataTypes.toArray(new String[dataTypes.size()]));

        close();//close connection, statement, resultset

    }

    public class UserInfo {

        /*
        @userID bigint,
        @username nvarchar(50) = NULL,
        @password nvarchar(255) = NULL,
        @email nvarchar(255) = NULL,
        @firstname nvarchar(50) = NULL,
        @lastname nvarchar(50) = NULL,
        @address1 nvarchar(50) = NULL,
        @address2 nvarchar(50) = NULL,
        @city nvarchar(50) = NULL,
        @state nvarchar(2) = NULL,
        @zipcode nvarchar(50) = NULL
         */
        private long userID;
        private String username;
        private String password;
        private String email;
        private String firstname;
        private String lastname;
        private String address1;
        private String address2;
        private String city;
        private String state;
        private String zipcode;

        public UserInfo(long userID, String email, String firstname,
                String lastname, String address1, String address2, String city,
                String state, String zipcode) {
            
            this.userID = userID;
            this.email = email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.address1 = address1;
            this.address2 = address2;
            this.city = city;
            this.state = state;
            this.zipcode = zipcode;
        }

        public long getUserID() {
            return userID;
        }

        public void setUserID(long userID) {
            this.userID = userID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

    }

} //End Subclass UserDAO
