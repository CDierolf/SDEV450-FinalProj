package Views.MyAccountView;

/**
 * @Course: SDEV 450 ~ Java Programming I
 * @Author Name: Stephen Graybeal
 * @Assignment Name: Views.MyAccountView
 * @Date: Dec 4, 2019
 * @Subclass MyAccountViewController Description:
 */
//Imports
import Classes.Utilities.Validation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Views.DashboardView.DashboardViewController;
import Views.ChangePasswordView.ChangePasswordViewController;
import java.sql.SQLException;
import Classes.Database.dao.UserDAO;
import Classes.Utilities.Alerts;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
//Begin Subclass MyAccountViewController

public class MyAccountViewController extends Validation implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfAddress1;
    @FXML
    private TextField tfAddress2;
    @FXML
    private TextField tfCity;
    @FXML
    private ChoiceBox cbState;
    @FXML
    private TextField tfZip;
    @FXML
    private Button btnChangePassword;
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
        //fill up State choicebox
        cbState.setItems(FXCollections.observableArrayList("", "AK", "AL", "AR",
                "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA",
                "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI",
                "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
                "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
                "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"));
        cbState.getSelectionModel().selectFirst();

        //set input limit to 5 numbers for tfZip
        //from https://stackoverflow.com/questions/56446127/javafx-textfield-with-regex-for-zipcode
        final Pattern pattern = Pattern.compile("\\d{0,5}");
        TextFormatter<?> formatter = new TextFormatter<>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                return change; // allow this change to happen
            } else {
                return null; // prevent change
            }
        });
        tfZip.setTextFormatter(formatter);

        UserDAO udao = new UserDAO();
        udao.init();
        ResultSet rs;
        try {
            rs = udao.getUser(dvc.getUser().getUserID());

            /* SELECT [userID], [username], [password], [email], [firstname], 
            [lastname], [address1], [address2], [city], [state], [zipcode] */
            while (rs.next()) {
                this.tfEmail.setText(rs.getString("email"));
                this.tfFirstName.setText(rs.getString("firstname"));
                this.tfLastName.setText(rs.getString("lastname"));
                this.tfAddress1.setText(rs.getString("address1"));
                this.tfAddress2.setText(rs.getString("address2"));
                this.tfCity.setText(rs.getString("city"));
                String state = rs.getString("state");
                if ((state == null) || (state.isEmpty())) {
                    //do nothing
                } else {
                    cbState.getSelectionModel().select(state);
                }
                this.tfZip.setText(rs.getString("zipcode"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(MyAccountViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public void update() {
        if (validateNoBlanks()) {
            if (Validation.validateEmail(tfEmail.getText())) {

                UserDAO udao = new UserDAO();
                udao.init();
                UserDAO.UserInfo info = udao.new UserInfo(dvc.getUser().getUserID(),
                        tfEmail.getText(), tfFirstName.getText(), tfLastName.getText(),
                        tfAddress1.getText(), tfAddress2.getText(), tfCity.getText(),
                        cbState.getSelectionModel().getSelectedItem().toString(),
                        tfZip.getText());
                udao.updateUserDetails(info);

                this.close();
            } else {
                Alerts.genericAlert("Invalid email", "Invalid email",
                        "Your email address is invalid.\nPlease enter an email"
                        + " address such as example@server.com").showAndWait();
            }

        }
    }
    public void openChangePasswordView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/Views/ChangePasswordView/ChangePasswordView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 200);
            // set the user to the dashboard view controller to maintain state
            ChangePasswordViewController controller = fxmlLoader.<ChangePasswordViewController>getController();
            controller.setDashboardController(this.dvc);
            Stage stage = new Stage();
            
            stage.setTitle("Change Password");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            this.close();
        } catch (IOException ex) {
            Logger.getLogger(MyAccountViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void close() {
        ((Stage) this.btnCancel.getScene().getWindow()).close();
    }

    private boolean validateNoBlanks() {

        boolean emailBlank = Validation.validateForBlankInput(
                tfEmail.getText(), "", false);
        boolean firstNameBlank = Validation.validateForBlankInput(
                tfFirstName.getText(), "", false);
        boolean lastNameBlank = Validation.validateForBlankInput(
                tfLastName.getText(), "", false);
        boolean address1Blank = Validation.validateForBlankInput(
                tfAddress1.getText(), "", false);
        boolean cityBlank = Validation.validateForBlankInput(
                tfCity.getText(), "", false);
        String state = cbState.getSelectionModel().getSelectedItem().toString();
        boolean stateBlank = !((state == null) || (state.isEmpty()));
        boolean zipBlank = Validation.validateForBlankInput(
                tfZip.getText(), "", false);

        boolean validationArray[] = {emailBlank, firstNameBlank, lastNameBlank,
            address1Blank, cityBlank, stateBlank, zipBlank};

        boolean validForm = false;

        for (boolean b : validationArray) {
            if (!b) {
                if (b == emailBlank) {
                    Alerts.blankFieldAlert("email").showAndWait();
                    tfEmail.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == firstNameBlank) {
                    Alerts.blankFieldAlert("first name").showAndWait();
                    tfFirstName.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == lastNameBlank) {
                    Alerts.blankFieldAlert("last name").showAndWait();
                    tfLastName.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == address1Blank) {
                    Alerts.blankFieldAlert("address 1").showAndWait();
                    tfAddress1.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == cityBlank) {
                    Alerts.blankFieldAlert("city").showAndWait();
                    tfCity.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == stateBlank) {
                    Alerts.blankFieldAlert("state").showAndWait();
                    cbState.requestFocus();
                    validForm = false;
                    break;
                }
                if (b == zipBlank) {
                    Alerts.blankFieldAlert("zip code").showAndWait();
                    tfZip.requestFocus();
                    validForm = false;
                    break;
                }

            } else {
                validForm = true;
            }
        }
        return validForm;
    }

} //End Subclass MyAccountViewController
