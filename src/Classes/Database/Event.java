package Classes.Database;
/**
*  @Course: SDEV 250 ~ Java Programming I
*  @Author Name: Stephen Graybeal
*  @Assignment Name: Classes.Database
*  @Date: Oct 12, 2019
*  @Subclass Event Description:
*/
//Imports
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

//Begin Subclass Event
public class Event {
    long eventID;
    int venueID;
    String eventname;
    //java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(); //ph
    java.util.Date startDate = new java.util.Date();
    //maybe add time here
    java.util.Date dateTBA = new java.util.Date();
    //maybe add time here
    String category; //TODO: maybe a class or enum for these
    String genre;
    double price;
    String info;
    
    
} //End Subclass Event