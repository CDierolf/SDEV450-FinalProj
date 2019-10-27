/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.LoginView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginViewController implements Initializable {

    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private TextField userNameText;
    @FXML
    private PasswordField passwordText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void openCreateAccountView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Views/CreateAccountView/CreateAccountView.fxml"));
            /* 
         * if "fx:controller" is not set in fxml
         * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Create New Account");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }

    public void openDashboard() throws IOException {
        // TODO 
        // Authenticate User
        // If Authenticated, display Dashbaord close LoginView
        // If Not Authenticated, display error
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Views/DashboardView/DashboardView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1461, 831);
        Stage stage = new Stage();
        stage.setTitle("Welcome ***USERNAME***");
        stage.setScene(scene);
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public void closeApp() throws IOException {
       
        Platform.exit();
        System.exit(0);

    }

}
