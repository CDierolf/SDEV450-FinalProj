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
    private Date eventTime;
    private String eventPrice;
    private List<Seat> seats = new ArrayList<>();

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }
    
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }
    public List<Seat> getSeats() {
        return this.seats;
    }
    
    public String getEventPrice() {
        return this.eventPrice;
    }
    public Date getEventTime() {
        return this.eventTime;
    }
    public String getEventName() {
        return this.eventName;
    }
    public Date getEventDate() {
        return this.eventDate;
    }

} //End Subclass PurchasedEvent
