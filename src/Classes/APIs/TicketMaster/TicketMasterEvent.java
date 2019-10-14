package Classes.APIs.TicketMaster;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

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
            private String name;
            private String imageUrl;
            private double price;
            private List<Images> images;
            private Image eventImage;
            private List<PriceRanges> prices;
            private Dates dates;
            
            public double getPrice() {
                if (prices == null) {
                    this.price = 18.00;
                }
                
                return this.price;
            }
            
            public String getName() {
                return this.name;
            }
            public Dates getDates() {
                return this.dates;
            }
            
            public Image getEventImage() {
                eventImage = new Image(getImageUrl());
                return eventImage;
            }
            public String getImageUrl() {
                // Get the smallest available event image from 
                // the API's image list.
                return images.get(9).getImageUrl();
            }
            

            public Dates getEventDates() {
                return this.dates;
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
                private String max;
                
                public String getPrice() {
                    return this.max;
                }
            }

        }

    }

} //End Subclass Event
