package Classes.Email;

import Views.SeatMaps.Venue.Seat;
import java.util.ArrayList;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Email
 * @Date: Nov 8, 2019
 * @Subclass Messages Description: 
 */
//Imports

//Begin Subclass Messages
public class Messages {
    
    public static String purchasedEventMessage(String event, ArrayList<Seat> seats, String price, String recipient) {
        String message = "Dear " + recipient + "\n\n";
        message += "This email is a confirmation of your ticket purchase for:\n\n";
        message += event + "\n\n";
        message += "This email will service as your ticket for the event. \n";
        message += "If you inadvertently delete this email, simply log back into"
                + "the app, click \"View Purchased Events\" and click \"Resend Ticket\"";
        message += "\n\nEvent: " + event + "\n";
        message += "Total Ticket Price: $" + price + "\n\n";
        message += "Seats Purchased:\n";
        for (Seat seat : seats) {
            message += seat.getDescription() + "\n";
        }
        message += "\n\nThank you, and enjoy the show!\nTicketAmateur";
        
        return message;
    }
    
    public static String accountCreationMessage(String recipient, String username, String password) {
        String message = "Dear " + recipient + "\n\n";
        message += "This message confirms your account creation with the TicketAmateur App\n";
        message += "Your credentials are as follows: \n\n";
        message += "Username: " + username + "\n";
        message += "Password: " + password + "\n";
        
        return message;
    }

} //End Subclass Messages