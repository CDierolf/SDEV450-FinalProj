package Classes.APIs.TicketMaster;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    public static class Embedded {

        public List<Events> events = new ArrayList<>();

        public List<Events> getEvents() {
            return events;
        }

        public int getNumberOfEvents() {
            return this.events.size();
        }

        public class Events {

            private String name;
            private String imageUrl;
            private String id;
            private double price;
            private List<Images> images;
            private Image eventImage;
            private List<PriceRanges> priceRanges;
            private Dates dates;
            private VenueData _embedded;

            public VenueData getVenueData() {
                return this._embedded;
            }

            public String getPrice() {
                if (priceRanges != null)
                    return this.priceRanges.get(0).getPrice();
                else {
                    return "TBD";
                }
            }

            public String getEventID() {
                return this.id;
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
                        if (this.localDate != null) {
                            return this.localDate;
                        } else {
                            return "TBD";
                        }
                    }

                    public String getEventLocalTime() {
                        if (this.localTime != null) {
                            return this.localTime;
                        } else {
                            return "TBD";
                        }
                    }
                }
            }

            public class PriceRanges {

                private String max;

                public String getPrice() {
                    if (this.max != null) {
                        setPrice();
                        return max;
                    } else {
                        return "TBD";
                    }
                }

                private void setPrice() {
                    if (Double.parseDouble(max) > 100) {
                        Random r = new Random();
                        double randomValue = 30.00 + (98.00 - 30.00) * r.nextDouble();
                        DecimalFormat twoPlaces = new DecimalFormat("0.00");
                        this.max = twoPlaces.format(randomValue);
                    }
                }
            }

            public class VenueData {

                private List<Venue> venues = new ArrayList<>();

                public List<Venue> getVenues() {
                    return this.venues;
                }

                public class Venue {

                    private String name;
                    private String id;
                    private String url;
                    private VenueImage image;
                    private VenueCity city;
                    private VenueState state;
                    private VenueAddress address;
                    private String postalCode;
                    private List<VenueImage> images = new ArrayList<>();

                    public String getVenueName() {
                        return this.name;
                    }

                    public String getVenueId() {
                        return this.id;
                    }

                    public String getVenueUrl() {
                        return this.url;
                    }

                    public String getVenuePostalCode() {
                        return this.postalCode;
                    }

                    public String getVenueImageUrl() {
                        return this.image.getVenueImageUrl();
                    }

                    public String getVenueCity() {
                        return this.city.getVenueCity();
                    }

                    public String getVenueState() {
                        return this.state.getVenueState();
                    }

                    public String getVenueAddress() {
                        return this.address.getVenueAddress();
                    }

                    public class VenueImage {

                        private String url;

                        public String getVenueImageUrl() {
                            if (this.url != null) {
                                return this.url;
                            } else {
                                return null;
                            }
                        }
                    }

                    public class VenueCity {

                        public String name;

                        public String getVenueCity() {
                            if (this.name != null) {
                                return this.name;
                            } else {
                                return "Not Available";
                            }
                        }
                    }

                    public class VenueState {

                        public String name;

                        public String getVenueState() {
                            if (this.name != null) {
                                return this.name;
                            } else {
                                return "Not Available";
                            }
                        }
                    }

                    public class VenueAddress {

                        public String line1;

                        public String getVenueAddress() {
                            if (this.line1 != null) {
                                return this.line1;
                            } else {
                                return "Not Available";
                            }
                        }
                    }
                }
            }

        }

    }

} //End Subclass Event
