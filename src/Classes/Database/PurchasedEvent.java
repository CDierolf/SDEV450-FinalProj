package Classes.Database;

import java.util.Date;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Database
 * @Date: Nov 17, 2019
 * @Subclass PurchasedEvent Description: 
 */
//Imports

//Begin Subclass PurchasedEvent
public class PurchasedEvent {

    private String eventName;
    private Date eventDate;
    private String eventSeat;
    private String eventRow;

    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    public void setEventSeat(String eventSeat) {
        this.eventSeat = eventSeat;
    }
    public void setEventRow(String eventRow) {
        this.eventRow = eventRow;
    }
    
    public String getEventName() {
        return this.eventName;
    }
    public Date getEventDate() {
        return this.eventDate;
    }
    public String getEventSeat() {
        return this.eventSeat;
    }
    public String getEventRow() {
        return this.eventRow;
    }
    
} //End Subclass PurchasedEvent