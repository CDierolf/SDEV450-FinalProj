package Classes.Objects;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Database
 * @Date: Nov 24, 2019
 * @Subclass Venue Description: 
 */
//Imports

//Begin Subclass Venue
public class Venue {
    
    private String venueCity;
    private String venueState;
    private String venueName;
    
    
    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }
    public void setVenueState(String venueState) {
        this.venueState = venueState;
    }
    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
    
    public String getVenueCity() {
        return this.venueCity;
    }
    public String getVenueState() {
        return this.venueState;
    }
    public String getVenueName() {
        return this.venueName;
    }

} //End Subclass Venue