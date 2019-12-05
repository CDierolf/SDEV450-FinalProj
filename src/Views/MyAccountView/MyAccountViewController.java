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
import java.sql.SQLException;
import Classes.Database.dao.UserDAO;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.scene.control.TextFormatter;
//Begin Subclass MyAccountViewController

public class MyAccountViewController extends Validation implements Initializable {

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
    
    public void openChangePasswordView(){
        UserDAO udao = new UserDAO();
        udao.init();
        ResultSet rs;
    }
    
    public void update(){
        //TODO do validation stuff
        
    }

    public void close() {
        ((Stage) this.btnCancel.getScene().getWindow()).close();
    }

} //End Subclass MyAccountViewController
