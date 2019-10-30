package Classes.Database;

import Classes.Utilities.Validation;

/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Classes.Database
 * @Date: Oct 12, 2019
 * @Subclass User Description:
 */
//Imports
public class User extends Validation{

    long userID;
    String username;
    String password;
    String email;
    
    
    public User(String uName, String password, String email) {

        this.username = uName;
        this.password = password;
        this.email = email;
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
    
    
    
    
    
    
    

} //End Subclass Userpackage Classes.Database;

