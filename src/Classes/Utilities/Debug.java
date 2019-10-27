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
  
    default String myToString(String[] theArray) {
        String result = "[";
        for (int i = 0; i < theArray.length; i++) {
           if (i > 0) {
              result = result + ",";
           }
           String item = theArray[i];
           result = result + item;
        }
        result = result + "]";
        return result;
     }
    
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
       /**
     * 
     * @param message
     * @param level 
     */

    default void logDebug(String message, String level) {
        this.logDebug(message, level, true);
    }
    
    /**
     * 
     * @param message 
     */

    default void logDebug(String message) {
        this.logDebug(message, "info");
    }
            /**
     * 
     * @param message
     * @param level
     * @param robust 
     */

    default void logDebug(String message, String level, boolean robust) {
        String callingMethod = Thread.currentThread().getStackTrace()[3].getMethodName();
        String lineNumber = Integer.toString(Thread.currentThread().getStackTrace()[3].getLineNumber());
        if (level.length() == 0) {
            level = "info";
        }
        
        if(robust) {
            message = message  + " From:" + callingMethod + " linenumber:" + lineNumber; 
        }
        if (getLogToFile()) {
            // log to file
            if("info".equalsIgnoreCase(level)) {
                logger.info(message);
            }else{
                // level = error
                //logger.error(message);
            }
        } else { // log debugging messages to output
            System.out.println(message);
        }
    }

} //End Subclass Debug