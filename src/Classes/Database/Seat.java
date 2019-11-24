package Classes.Database;

/**
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Stephen
 * @Assignment Name: Classes.Database
 * @Date: Oct 12, 2019
 * @Subclass Seat Description:
 */
//Imports
public class Seat {
    private String row;
    private String seat;
    
    public void setSeat(String seat) {
        this.seat = seat;
    }
    public void setRow(String row) {
        this.row = row;
    }
    
    public String getSeat() {
        return this.seat;
    }
    
    public String getRow() {
        return this.row;
    }

} //End Subclass Seat
