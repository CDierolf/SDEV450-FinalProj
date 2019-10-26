/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.CreateAccountView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public class CreateAccountViewController implements Initializable {

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
        // TODO Validate input
        // Create new validation class in classes/utilities
        // validate email with regex

        return true;

    }
    
    public void createNewUserAccount() {
        if (validateAccountInput()) {
            handleCloseButtonAction();
        } else {
            // DISPLAY ERROR
        }
    }

    private void handleCloseButtonAction() {
        Stage stage = (Stage) createAccountButton.getScene().getWindow();
        stage.close();
    }

}
