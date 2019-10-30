package Classes.Utilities;

import javafx.scene.control.Alert;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Utilities
 * @Date: Oct 30, 2019
 * @Subclass Alerts Description: 
 */
//Imports

//Begin Subclass Alerts
public class Alerts {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    
    /**
     * Provides an alert for a blank field.
     * @return 
     */
    public Alert blankFieldAlert(String fieldID) {
        alert.setTitle("Blank Field Found");
        alert.setHeaderText(fieldID + " cannot be blank.");
        alert.setContentText("Please provide a valid value for "
                + fieldID + ".");
        
        return alert;
    }
    
    /**
     * Provides an alert for invalid characters within a field.
     * @return 
     */
    public Alert invalidIntegerAlert(String fieldID) {
        alert.setTitle("Invalid Value Entered");
        alert.setHeaderText(fieldID + " must have at least one number\n"
                + "and contain numbers only.");
        alert.setContentText("Please provide a valid value for " + fieldID);
        
        return alert;
    }
    
    public Alert invalidDoubleAlert(String fieldID) {
        alert.setTitle("Invalid Decimal Value Entered");
        alert.setHeaderText(fieldID + " must contain a decimal value only without 0's");
        alert.setContentText("Please provide a valid decimal value for " + fieldID);
        
        return alert;
    }
    
    public Alert invalidNameAlert(String fieldID) {
        alert.setTitle("Invalid Name");
        alert.setHeaderText(fieldID + " must contain at least one character,"
                + "\nand cannot contain numbers or special characters.");
        alert.setContentText("Please provide a valid value for " + fieldID);
        
        return alert;
    }
    
    public Alert stackEmptyAlert() {
        alert.setTitle("Nothing to Sort");
        alert.setHeaderText("There is no longer any values to remove from the"
                + " list.");
        alert.setContentText("You must add values to the list first.");
        
        return alert;
    }
    
    public Alert genericAlert(String title, String header, String content) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        return alert;
    }

} //End Subclass Alerts