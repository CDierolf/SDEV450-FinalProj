package Classes.Database;
/**
*  @Course: SDEV 250 ~ Java Programming I
*  @Author Name: Stephen Graybeal
*  @Assignment Name: Classes.Database
*  @Date: Oct 12, 2019
*  @Subclass Event Description:
*/
//Imports

import java.util.Date;
import java.text.SimpleDateFormat;
import Classes.Database.DatabaseInterface;

//Begin Subclass Event
public class Event {
    String eventID;
    int venueID;
    String eventname;
    //java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(); //ph
    Date startDate = new Date();
    //maybe add time here
    Date dateTBA = new Date();
    //maybe add time here
    String category; //TODO: maybe a class or enum for these
    String genre;
    double price;
    String info;
    
    public Event(String eventID){
        
    }
    
    public static Date toDate(String date){
        Date myDate=new Date();
        SimpleDateFormat APIDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        try{
            myDate=APIDate.parse(date);
        }
        catch(Exception e){
            //time is probably TBD
            APIDate=new SimpleDateFormat("yyyy-mm-dd");
            try{
                myDate=APIDate.parse(date);
                myDate.setHours(14);
                myDate.setMinutes(14);
            }
            catch(Exception f){
                System.out.println(f.toString());
            }
        }
        
        return myDate;
    }
    
    
} //End Subclass Event