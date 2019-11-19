package Classes.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Seat> seats = new ArrayList<>();

    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    public List<Seat> getSeats() {
        return this.seats;
    }
    
    public String getEventName() {
        return this.eventName;
    }
    public Date getEventDate() {
        return this.eventDate;
    }
    
    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }
    
} //End Subclass PurchasedEvent