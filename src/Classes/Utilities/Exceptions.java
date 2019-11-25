package Classes.Utilities;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Utilities
 * @Date: Oct 30, 2019
 * @Subclass Exceptions Description: 
 */
//Imports

//Begin Subclass Exceptions
public class Exceptions{
    
    public static class InvalidUserException extends Exception {
        public InvalidUserException() {}
        public InvalidUserException(String message) {
            super(message);
        }
    }
    
    public static class EventIdIsNullException extends RuntimeException {
        public EventIdIsNullException() {}
        public EventIdIsNullException(String message) {
            super(message);
        }
    }

} //End Subclass Exceptions