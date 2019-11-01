/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.CreateAccountView;

import Classes.Utilities.Alerts;
import Classes.Utilities.Enums.FieldEnum;
import Classes.Utilities.Validation;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris Dierolf
 */
public class CreateAccountViewController extends Validation implements Initializable {

    @FXML
    private Button createAccountButton;
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField retypePasswordText;
    @FXML
    private TextField emailText;

    Alerts alerts = new Alerts();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Validate password matching and valid email regex
    public boolean validateAccountInput() {
        boolean validPasswords = Validation.validatePassword(this.passwordText, 
                this.retypePasswordText);
        boolean validEmail = Validation.validateEmail(this.emailText.getText());
        
        return validPasswords && validEmail;
    }

    // Ensure all fields contain data.
    public boolean validatePopulatedFields() {
        boolean usernameBlank = Validation.validateForBlankInput(this.userNameText.getText(), "-Username-", false);
        boolean passwordTextBlank = Validation.validateForBlankInput(this.passwordText.getText(), "-Password-", false);
        boolean passwordTextTwoBlank = Validation.validateForBlankInput(this.retypePasswordText.getText(), "-Password-", false);
        boolean emailTextBlank = Validation.validateForBlankInput(this.emailText.getText(), "-Email-", false);

        boolean validationArray[] = {usernameBlank, passwordTextBlank, passwordTextTwoBlank, emailTextBlank};
        
        boolean validForm = false;

        for (boolean b : validationArray) {
            if (!b) {
                if (b == usernameBlank) {
                    showAlert(FieldEnum.USERNAME_FIELD);
                    this.userNameText.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == passwordTextBlank) {
                    showAlert(FieldEnum.FIRST_PASSWORD_FIELD);
                    this.passwordText.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == passwordTextTwoBlank) {
                    showAlert(FieldEnum.SECOND_PASSWORD_FIELD);
                    this.retypePasswordText.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == emailTextBlank) {
                    showAlert(FieldEnum.EMAIL_FIELD);
                    this.emailText.requestFocus();
                    validForm = false;
                    break;
                }
            } else {
                validForm = true;
            }
        }
        return validForm;
    }

    // Show alerts based on context
    private void showAlert(FieldEnum field) {
        switch (field) {
            case USERNAME_FIELD:
                Alerts.genericAlert("Username cannot be blank", "",
                        "Please provide a valid username.").showAndWait();
                break;
            case FIRST_PASSWORD_FIELD:
                alerts.genericAlert("Please ensure both passwords match", "",
                        "Please provide valid and matching passwords").showAndWait();
                break;
            case SECOND_PASSWORD_FIELD:
                alerts.genericAlert("Please ensure both passwords match", "",
                        "Please provide valid and matching passwords").showAndWait();
                break;
            case EMAIL_FIELD:
                alerts.genericAlert("Email cannot be blank", "",
                        "Please provide a valid email address").showAndWait();
                break;
            default:
                alerts.genericAlert("Please provide information for all fields", "",
                        "").showAndWait();
                break;
        }
    }

    // Validate and create new user in backend.
    public void createNewUserAccount() {
        
        if (validatePopulatedFields() && validateAccountInput()) {
            // TODO 
            // Submit data to backend
        }
    }

    private void handleCloseButtonAction() {
        Stage stage = (Stage) createAccountButton.getScene().getWindow();
        stage.close();
    }

}
