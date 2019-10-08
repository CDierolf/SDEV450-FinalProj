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

    public Embedded getEmbeddedEvents() {
        return this._embedded;
    }

    public class Embedded {

        public List<Events> events = new ArrayList<>();

        public List<Events> getEvents() {
            return events;
        }

        public class Events {

            public String name;
            public String url;
            public List<Images> images;
            public Dates dates;
            public List<PriceRanges> priceRanges;

            public String getEventName() {
                return this.name;
            }

            public String getEventUrl() {
                return this.url;
            }

            public List<Images> getEventImages() {
                return this.images;
            }

            public Dates getEventDates() {
                return this.dates;
            }

            public List<PriceRanges> getPriceRange() {
                return this.priceRanges;
            }

            public class Images {

                private String url;
                private int width;
                private int height;
                
                public String getImageUrl() {
                    return this.url;
                }
                public int getImageWidth() {
                    return this.width;
                }
                public int getImageHeight() {
                    return this.height;
                }
            }

            public class Dates {

                public Start start;
                
                public Start getEventStartData() {
                    return this.start;
                }

                public class Start {

                    public String localDate;
                    public String localTime;
                    
                    public String getEventLocalDate() {
                        return this.localDate;
                    }
                    public String getEventLocalTime() {
                        return this.localTime;
                    }

                }
            }

            public class PriceRanges {

                public double max;
                
                public double getEventPrice() {
                    return this.max;
                }
            }

        }

    }

} //End Subclass Event
