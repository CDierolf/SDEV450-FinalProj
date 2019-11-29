/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.LoginView;

import Classes.APIs.TicketMaster.TicketMasterEvent;
import Classes.Objects.User;
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
import Classes.Utilities.Alerts;
import Views.DashboardView.DashboardViewController;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import jdk.nashorn.internal.runtime.Context;

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

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public void openDashboard() throws IOException, NoSuchAlgorithmException, SQLException {

        loginUser();
        //this.setUser(user); // sets the user object on the screen

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Views/DashboardView/DashboardView.fxml"));
        //Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.load(), 1461, 831);
        // set the user to the dashboard view controller to maintain state
        DashboardViewController controller = fxmlLoader.<DashboardViewController>getController();
        controller.setUser(user);
        Stage stage = new Stage();

        stage.setTitle("Welcome " + this.userNameText.getText());
        stage.setScene(scene);
        //stage.initStyle(Sta geStyle.TRANSPARENT);

        stage.show();
    }

    // Login user on separate thread
    private void loginUser() {
        //Thread thread = new Thread(() -> {
            //Runnable run = () -> {
                try {
                    // Authenticate User
                    user = new User(this.userNameText.getText(),
                            this.passwordText.getText());
                    long userid = user.loginUser();
                    user.setUserID(userid);
                    // If Authenticated, display Dashbaord close LoginView
                    // If Not Authenticated, display error and return to screen
                    if (userid == 0) {
                        this.userNameText.setText("");
                        this.passwordText.setText("");
                        return;
                    }
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            //};
            //Platform.runLater(run);
        //});

        //thread.setDaemon(true);
        //thread.start();
    }

    public void closeApp() throws IOException {

        Platform.exit();
        System.exit(0);

    }

}
