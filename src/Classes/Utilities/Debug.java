package Classes.Utilities;





/** 
 * @Course: SDEV 250 ~ Java Programming I
 * @Author Name: Tom Muck
 * @Assignment Name: 
 * @Date: Aug 10, 2018
 * @Subclass Debug Description: 
 */
//Imports


import java.util.logging.Logger;
import sdev_450_ticketmanager.SDEV_450_TicketManager;
//Begin Subclass Debug

/**
 *
 * @author Tom.Muck
 */
public interface Debug {

    /**
     *
     */
    final static Logger logger = Logger.getLogger(SDEV_450_TicketManager.class.toString());
    
    /**
     *
     * @return
     */
    public boolean getLogToFile();

    /**
     *
     * @param logToFile
     */
    public void setLogToFile(boolean logToFile);
  
    /**
     *
     * @param message
     */
    public void logDebug(String message);
    
    /**
     *
     * @param message
     * @param level
     */
    public void logDebug(String message, String level);
    
    /**
     * 
     * @param message
     * @param level
     * @param robust 
     */
    public void logDebug(String message, String level, boolean robust);
    
    /**
     *
     * @param debug
     */
    public void setDebug(boolean debug);
    
    /**
     *
     * @return
     */
    public boolean getDebug();
    
    public String myToString(String[] theArray);
} //End Subclass Debug