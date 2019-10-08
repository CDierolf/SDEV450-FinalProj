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
public class TicketMasterEvent {

    public Embedded _embedded;

    public class Embedded {

        public List<Events> events = new ArrayList<>();
        
    }

    public class Events {

        public String name;
        public String url;
        public List<Images> images;
        public Dates dates;

        public class Images {

            public String url;
            public int width;
            public int height;
        }
        
        public class Dates {
            public Start start;
            public class Start {
                public String localDate;
                public String localTime;
            }
        }
        
        
    }
    
    

} //End Subclass Event
