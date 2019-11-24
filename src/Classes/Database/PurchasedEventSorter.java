package Classes.Database;

import java.util.ArrayList;
import java.util.Collections;

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
    
    private ArrayList<PurchasedEvent> purchasedEventsList = new ArrayList<>();
    
    public PurchasedEventSorter(ArrayList<PurchasedEvent> pEventList) {
        this.purchasedEventsList = pEventList;
    }
    
    public ArrayList<PurchasedEvent> getSortedPurchasedEvents() {
        Collections.sort(purchasedEventsList);
        return purchasedEventsList;
    }

} //End Subclass PurchasedEventSorter