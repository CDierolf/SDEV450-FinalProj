package Classes.Objects;

import Classes.Utilities.Alerts;
import Classes.Utilities.Validation;
import java.security.NoSuchAlgorithmException;

/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Classes.Database
 * @Date: Oct 12, 2019
 * @Subclass User Description:
 */
//Imports
public class User extends Validation {

    long userID;
    String username;
    String password;
    String email;
    boolean loggedin = false;

    public boolean isLoggedin() {
        return loggedin;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String uName, String password, String email) {

        this.username = uName;
        this.password = password;
        this.email = email;
    }

    public User(String uName, String password) {

        this.username = uName;
        this.password = password;
    }

    public User() {

        //this.username = uName;
        //this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void addUser() throws NoSuchAlgorithmException {
        Classes.Database.dao.UserDAO dao = new Classes.Database.dao.UserDAO();
        dao.init();
        this.setUserID(dao.addUser(
                this.getUsername(),
                this.getPassword(),
                this.getEmail()
        ));
        dao.close();
    }

    public long loginUser() throws NoSuchAlgorithmException {
        Classes.Database.dao.UserDAO dao = new Classes.Database.dao.UserDAO();
        long myuserID = dao.loginUser(
                this.getUsername(),
                this.getPassword()
        );
        if (myuserID == 0) {
            Alerts.genericAlert("User login failed", "User login failed", "User login failed").showAndWait();
        } else {
            this.setLoggedin(true);
            this.setUserID(myuserID);
            System.out.println("User logged in: " + this.username + ", ID: " + this.getUserID());
        }
        dao.close();
        return this.getUserID();
    }

    public long getUserID() {
        return userID;
    }

} //End Subclass Userpackage Classes.Database;

