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
        public List<Events>events = new ArrayList<>();
    }
    public class Events {
        public String name;
    }
 
    
    

    
    
} //End Subclass Event