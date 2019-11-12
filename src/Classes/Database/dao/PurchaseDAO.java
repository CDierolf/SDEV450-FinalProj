package Classes.Database.dao;


/** 
 * @Course: SDEV 450 ~ Enterprise Java
 * @Author Name: Tom Muck
 * @Assignment Name: TicketManager
 * @Date: Nov 11, 2019
 * @Subclass PurchaseDAO Description: 
 */
//Imports

import Classes.Database.DatabaseInterface;
import Classes.Database.User;
import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//Begin Subclass PurchaseDAO

/**
 *
 * @author Tom.Muck
 */
public class PurchaseDAO extends DatabaseInterface  {
    public void makePurchase(User user, Events event, int[] seats) throws SQLException {
        init();
        // get the user
        StringBuilder sb = new StringBuilder();
        ArrayList<String> purchaseValues = new ArrayList<String>(); // just one param for this request
        ArrayList<String> dataTypes = new ArrayList<String>(); 
        String Q1 = "{ call [usp_UsersEventsInsert](?,?,?,?) }";
        /*
        @EventId varchar(255),
        @UserId bigint,    
        @Tax money = NULL,
	@seats varchar(max)
        */
       
        purchaseValues.add(event.getEventID());//event id
        dataTypes.add("string");
        purchaseValues.add(Long.toString(user.getUserID()));//userid
        dataTypes.add("int");
        purchaseValues.add("0");//tax
        dataTypes.add("string");
        // now do the seats
        for(int i=0; i<seats.length;i++){
            if(sb.length() != 0){
                sb.append(",");
            }
            sb.append(Integer.toString(seats[i]));
        }
        String seatstring = sb.toString();
        purchaseValues.add(seatstring);//seats
        dataTypes.add("string");
        callableStatement(Q1, purchaseValues.toArray(
                new String[purchaseValues.size()]), 
                dataTypes.toArray(new String[dataTypes.size()]));
        

        return;
    }
    

    
    
    
    
    
    
    
} //End Subclass UserDAO