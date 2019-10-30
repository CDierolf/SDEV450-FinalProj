/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.CreateAccountView;

import Classes.Utilities.Validation;
import java.net.URL;
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
 * @author pis7ftw
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public boolean validateAccountInput() {
        boolean isValidPassword = validatePassword(this.passwordText.getText(), 
                this.retypePasswordText.getText());
        boolean isValidEmail = validateEmail(this.emailText.getText());

        return isValidPassword && isValidEmail;

    }
    
    public boolean validatePopulatedFields() {
        boolean userNameBlank = validateForBlankInput(this.userNameText.getText(), "Username", false);
        boolean firstPasswordBlank = validateForBlankInput(this.passwordText.getText(), "Password", false);
        boolean secondPasswordBlank = validateForBlankInput(this.retypePasswordText.getText(), "Password", false);
        boolean emailBlank = validateForBlankInput(this.emailText.getText(), "Email", false);
    }
    
    public void createNewUserAccount() {
        if (!validateAccountInput() || !validatePopulatedFields()) {
            // Show err's
        } else {
            handleCloseButtonAction();
        }
    }

    private void handleCloseButtonAction() {
        Stage stage = (Stage) createAccountButton.getScene().getWindow();
        stage.close();
    }
    
    

}
