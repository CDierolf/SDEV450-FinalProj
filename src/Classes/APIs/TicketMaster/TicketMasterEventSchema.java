package Classes.APIs.TicketMaster;

import java.util.ArrayList;
import java.util.List;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.APISchema
 * @Date: Sep 24, 2019
 * @Subclass Event Description: 
 */
//Imports

//Begin Subclass Event
public class TicketMasterEventSchema {
    // List of Events
    private List<Events> events = new ArrayList<>();
    
    public class Genre {
        private String genreName;
        
        public String getGenreName() {
            return this.genreName;
        }
    }
    
    public class Classifications {
        public Genre genre;
        
        public Genre getGenre() {
            return this.genre;
        }
    }
    
    public class Dates {
        private String localDate;
        private String localTime;
        private String dateTime;
        
        public String getLocalDate() {
            return this.localDate;
        }
        public String getLocalTime() {
            return this.localTime;
        }
        public String getDateTime() {
            return this.dateTime;
        }
    }
    
    public class Images {
        private String ratio;
        private String url;
        private int width;
        private int height;
        
        public String getRatio() {
            return this.ratio;
        }
        public String getURL() {
            return this.url;
        }
        public int getWidth() {
            return this.width;
        }
        public int getHeight() {
            return this.height;
        }
    }
    public class Events {
        private String name;
        private Images images;
        private Dates dates;
        
        public String getName() {
            return this.name;
        }
        public Images getImages() {
            return this.images;
        }
        public Dates getDates() {
            return this.dates;
        }
    }


} //End Subclass Event