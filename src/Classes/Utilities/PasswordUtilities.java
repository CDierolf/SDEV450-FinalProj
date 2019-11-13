package Classes.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: Classes.Utilities
 * @Date: Nov 9, 2019
 * @Subclass PasswordUtilities Description: 
 */
//Imports

//Begin Subclass PasswordUtilities
public class PasswordUtilities {
    
    public PasswordUtilities(){

        
    }
    
    /*
    
    code modified from 
    https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
    using "medium" security MD5 functions
    */
    public String getHashedPassword(String pw) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(pw.getBytes());
        //Get the hash's bytes 
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        String encryptedPassword = sb.toString();        
        return encryptedPassword;
    }
} //End Subclass PasswordUtilities