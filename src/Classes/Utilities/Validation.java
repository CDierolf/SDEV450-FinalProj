package Classes.Utilities;

import java.util.List;
import javafx.scene.control.CheckBox;

/** 
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.Utilities
 * @Date: Oct 30, 2019
 * @Subclass Validation Description: 
 */
//Imports

//Begin Subclass Validation
public class Validation extends Alerts{
    
    private final String numericalRegex = "^[0-9]*$";
    private final String nameRegex = "^[A-Za-z\\s]*$";
    private final String doubleRegex = "-?\\d+(\\.\\d+)?";
    private final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)"
                        + "*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validates string against regex and displays alert if needed
     *
     * @param s
     * @param displayAlert
     * @return
     */
    public boolean validateIntegerInput(String s, String fieldID, boolean displayAlert) {

        String s1 = s.trim();
        boolean validRegex = matchesRegex(s, numericalRegex);
        boolean isValid = false;

        if (!validRegex || s1.isEmpty()) {
            isValid = false;
        } else {
            isValid = true;
        }
        
        if (displayAlert) {
            invalidIntegerAlert(fieldID).showAndWait();
        }
        
        return isValid;
    }
    
    /** OVERLOADED
     * Validates a string aginst a regex. Does not contain fieldID or display
     * Alert parameters.
     * @param s
     * @return 
     */
    public boolean validateIntegerInput(String s) {
        String s1 = s.trim();
        boolean validRegex = matchesRegex(s, numericalRegex);
        boolean isValid = false;
        
        if (!validRegex || s1.isEmpty()) {
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Validates if string contains data
     *
     * @param s
     * @return
     */
    public boolean validateForBlankInput(String s, String fieldID,
            boolean displayAlert) {

        boolean isInvalid = s.isEmpty() || s.trim().length() == 0;
        boolean inputValid = false;

        if (displayAlert) {
            if (isInvalid) {
                blankFieldAlert(fieldID).showAndWait();
                inputValid = false;
            } else {
                inputValid = true;
            }
        } else {
            if (isInvalid) {
                // Don't display alert
                inputValid = false;
            } else {
                inputValid = true;
            }
        }

        return inputValid;
    }

    /**
     * Validates a double value by parseDouble(s).
     *
     * @param s - string to be validated
     * @param fieldID - ID of the field containing String s
     * @param displayAlert - display alert (true/false)
     * @return
     */
    public boolean validateDoubleInput(String s, String fieldID, boolean displayAlert) {
        String s1 = s.trim();
        boolean isValid = false;
        boolean hasZero = String.valueOf(s1).contains("0");
        boolean validRegex = matchesRegex(s1, doubleRegex);

        if (!s1.isEmpty() && validRegex) {
            if (!hasZero) {
                isValid = true;
            } else {
                isValid = false;
            }
        } else if (s1.isEmpty()) {
            isValid = true;
        }

        if (displayAlert && !isValid) {
            invalidDoubleAlert(fieldID).showAndWait();
        }

        return isValid;
    }

    public boolean validateNameInput(String s, String fieldID, boolean displayAlert) {
        String s1 = s.trim();
        boolean isValid = false;
        boolean validRegex = matchesRegex(s1, nameRegex);

        if (!validRegex || s1.isEmpty()) {
            isValid = false;
        } else {
            isValid = true;
        }

        if (displayAlert && !isValid) {
            invalidNameAlert(fieldID).showAndWait();
        }

        return isValid;
    }

    /**
     * Validates if only one checkbox in lstChecks is selected.
     *
     * @param lstChecks
     * @return true only one selected, false more than one selected
     */
    public boolean oneCheckSelected(List<CheckBox> lstChecks, boolean displayAlert) {
        int numChecks = 0;
        boolean valid = false;
        for (CheckBox c : lstChecks) {
            if (c.isSelected()) {
                numChecks++;
            }
        }
        if (!(numChecks > 1)) {
            valid = true;
        } else {
            valid = false;
        }

        if (!valid && displayAlert) {
            genericAlert("Select One Checkbox", "One one checkbox may be selected",
                    "Please ensure that only one checkbox is selected.").showAndWait();
        }
        return valid;
    }
    
    public boolean validateFilePath(String s, String fieldID, boolean displayAlert) {
        String s1 = s.trim();
        boolean isValid = false;
        if (s1.isEmpty()) {
            isValid = false;
        } else {
            isValid = true;
        }
        if (!isValid && displayAlert) {
            genericAlert("File not loaded", "Unable to obtain data.", 
                    "Please load a file first.").showAndWait();
        }
        
        return isValid;
    }

    /**
     * Returns result of match between input and the provided regex expression
     *
     * @param input - String input
     * @param regex - String regex
     * @return
     */
    private boolean matchesRegex(String input, String regex) {
        return input.matches(regex);
    }

} //End Subclass ValidationUtils