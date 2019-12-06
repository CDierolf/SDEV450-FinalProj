package Views.ChangePasswordView;

/**
 * @Course: SDEV 450 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Date: Dec 4, 2019
 * @Subclass ChangePasswordViewController Description:
 */
//Imports
import Classes.Utilities.Validation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import Views.DashboardView.DashboardViewController;
import java.sql.SQLException;
import Classes.Database.dao.UserDAO;
import Classes.Utilities.Alerts;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.scene.control.TextFormatter;

public class ChangePasswordViewController extends Validation implements Initializable {

    @FXML
    private PasswordField pw1;
    @FXML
    private PasswordField pw2;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnUpdate;

    DashboardViewController dvc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;

    }

    public void update() {
        if (validateNoBlanks()) {
            if (Validation.validatePassword(pw1, pw2)) {
                UserDAO udao = new UserDAO();
                udao.init();
                try {
                    udao.changePassword(this.dvc.getUser().getUserID(), pw1.getText());
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(ChangePasswordViewController.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.close();
            } else {
                Alerts.genericAlert("Passwords do not match", "Passwords do not match",
                        "The passwords you have entered do not match").showAndWait();
            }
        }
    }

    public void close() {
        ((Stage) this.btnCancel.getScene().getWindow()).close();
    }

    private boolean validateNoBlanks() {

        boolean pw1Blank = Validation.validateForBlankInput(
                pw1.getText(), "", false);
        boolean pw2Blank = Validation.validateForBlankInput(
                pw2.getText(), "", false);

        boolean validationArray[] = {pw1Blank, pw2Blank};

        boolean validForm = false;

        for (boolean b : validationArray) {
            if (!b) {
                if (b == pw1Blank) {
                    Alerts.blankFieldAlert("first password").showAndWait();
                    pw1.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == pw2Blank) {
                    Alerts.blankFieldAlert("second password").showAndWait();
                    pw2.requestFocus();
                    validForm = false;
                    break;
                }

            } else {
                validForm = true;
            }
        }
        return validForm;
    }
}
