package Classes.APIs.TicketMaster;

import java.util.List;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.APIs.TicketMaster
 * @Date: Oct 8, 2019
 * @Subclass TicketMasterAttraction Description: 
 */
//Imports

//Begin Subclass TicketMasterAttraction
public class TicketMasterAttraction {
    public Embedded _embedded;
    
    public class Embedded {
        public List<Attractions> attractions;
        
        public class Attractions {
            public String name;
            public String url;
            
        }
    }

} //End Subclass TicketMasterAttraction