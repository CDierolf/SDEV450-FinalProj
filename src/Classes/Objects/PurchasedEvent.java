package Classes.Objects;

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
public class PurchasedEvent implements Comparable<PurchasedEvent>{

    private String eventName;
    private Date eventDate;
    private Date eventTime;
    private Double eventPrice;
    private String eventImageUrl;
    private List<Seat> seats = new ArrayList<>();
    private Venue venue = new Venue();

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

    public void setEventPrice(Double eventPrice) {
        this.eventPrice = eventPrice;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return this.venue;
    }

    public Double getEventPrice() {
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

    public String getEventImageUrl() {
        return this.eventImageUrl;
    }

    @Override
    public int compareTo(PurchasedEvent o) {
        return (this.getEventDate().compareTo(o.getEventDate()));
    }

} //End Subclass PurchasedEvent
