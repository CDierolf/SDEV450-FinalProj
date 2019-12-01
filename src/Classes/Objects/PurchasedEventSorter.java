package Classes.Objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Database
 * @Date: Nov 24, 2019
 * @Subclass PurchasedEventSorter Description: 
 */
//Imports

//Begin Subclass PurchasedEventSorter
public class PurchasedEventSorter {
    
    private List<PurchasedEvent> purchasedEventsList = new ArrayList<>();
    
    public PurchasedEventSorter(List<PurchasedEvent> pEventList) {
        this.purchasedEventsList = pEventList;
    }
    
    public List<PurchasedEvent> getSortedPurchasedEvents() {
        Collections.sort(purchasedEventsList);
        return purchasedEventsList;
    }

} //End Subclass PurchasedEventSorter