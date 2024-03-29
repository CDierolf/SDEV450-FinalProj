/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.PurchasingView;

import Classes.APIs.TicketMaster.TicketMasterEvent.Embedded.Events;
import Classes.Objects.User;
import Classes.Email.Messages;
import Classes.Email.SendEmail;
import Classes.Utilities.Alerts;
import Views.DashboardView.DashboardViewController;
import Views.SeatMaps.Venue.SeatMapSeat;
import Views.SeatSelectionView.SeatSelectionViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.mail.MessagingException;

/**
 * FXML Controller class
 *
 * @author Corbin
 */
public class PurchasingViewController implements Initializable {

    private SeatSelectionViewController svc;
    private boolean didCreditCardChangeManual;
    private String updateText;
    private ArrayList<Label> lblArray;
    private ArrayList<TextField> tfArray;

    // FXML Objects
    @FXML
    private TextField tfFName;
    @FXML
    private TextField tfLName;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfCity;
    @FXML
    private TextField tfState;
    @FXML
    private TextField tfZip;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCreditCard;
    @FXML
    private TextField tfExpiration;
    @FXML
    private TextField tfCardName;
    @FXML
    private TextField tfCVV;
    @FXML
    private Label lblFName;
    @FXML
    private Label lblLName;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblState;
    @FXML
    private Label lblZip;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblCreditCard;
    @FXML
    private Label lblExpiration;
    @FXML
    private Label lblCardName;
    @FXML
    private Label lblCVV;
    @FXML
    private Button btnPurchase;
    private Button btnBack;

    private Events event;
    private User user;
    DashboardViewController dvc;
    Alerts alerts = new Alerts();

    public void setDashboardController(DashboardViewController dvc) {
        this.dvc = dvc;
    }

    public DashboardViewController getDashboardController() {
        return dvc;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registerCCFieldListener();
        didCreditCardChangeManual = false;
    }

    // Setup validation labels
    public void setupValidation() {
        // Validation labels array
        List<Label> lables = Arrays.asList(lblFName, lblLName, lblAddress, lblCity,
                lblState, lblZip, lblEmail, lblCreditCard, lblExpiration, lblCardName, lblCVV);
        lblArray = new ArrayList<>();
        lblArray.addAll(lables);

        // Textfields array
        List<TextField> textFields = Arrays.asList(tfFName, tfLName, tfAddress, tfCity,
                tfState, tfZip, tfEmail, tfCreditCard, tfExpiration, tfCardName, tfCVV);
        tfArray = new ArrayList<>();
        tfArray.addAll(textFields);

        // Clear for initial view
        clearValidation();
    }

    private void clearValidation() {
        for (Label label : lblArray) {
            label.setText("Required Field");
            label.setVisible(false);
        }
    }

    private boolean validateFields() {
        System.out.println("Validating purchase fields");
        boolean validated = true;

        // Required fields
        int index = 0;
        for (TextField tf : tfArray) {
            if (tf.getText().length() == 0) {
                Label lbl = lblArray.get(index);
                lbl.setText("Required Field");
                lbl.setVisible(true);
                validated = false;
            }
            index++;
        }

        // Valid email address
        if (tfEmail.getText().length() > 0) {
            Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+"
                    + ")*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
                    Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(tfEmail.getText());
            if (!m.find()) {
                lblEmail.setText("Invalid Email");
                lblEmail.setVisible(true);
                validated = false;
            }
        }

        // Valid zip code
        if (tfZip.getText().length() > 0) {
            Pattern p = Pattern.compile("^[0-9]{5}(?:-[0-9]{4})?$");
            Matcher m = p.matcher(tfZip.getText());
            if (!m.find()) {
                lblZip.setText("Invalid Zip Code");
                lblZip.setVisible(true);
                validated = false;
            }
        }

        // Valid expiration date
        if (tfExpiration.getText().length() > 0) {
            Pattern p = Pattern.compile("^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$");
            Matcher m = p.matcher(tfExpiration.getText());
            if (!m.find()) {
                lblExpiration.setText("Invalid Date");
                lblExpiration.setVisible(true);
                validated = false;
            }
        }

        // Valid CVV
        if (tfCVV.getText().length() > 0) {
            Pattern p = Pattern.compile("^[0-9]{3}?$");
            Matcher m = p.matcher(tfCVV.getText());
            if (!m.find()) {
                lblCVV.setText("Invalid CVV");
                lblCVV.setVisible(true);
                validated = false;
            }
        }

        // Valid Credit Card Number
        if (tfCreditCard.getText().length() > 0) {
            Pattern p = Pattern.compile("^[0-9]{4}" + "-" + "[0-9]{4}" + "-" + "[0-9]{4}" + "-" + "[0-9]{4}");
            Matcher m = p.matcher(tfCreditCard.getText());
            if (!m.find() || tfCreditCard.getText().length() != 19) {
                lblCreditCard.setText("Invalid Credit Card Number");
                lblCreditCard.setVisible(true);
                validated = false;
            }
        }

        return validated;
    }

    // Purchase Tickets
    public void purchaseTickets() throws IOException {
        clearValidation();

        if (validateFields()) {
            btnPurchase.setDisable(true);
            System.out.println("Purchasing tickets...");
            Classes.Database.dao.PurchaseDAO dao = new Classes.Database.dao.PurchaseDAO();
            dao.init();
            long userid = this.getDashboardController().getUser().getUserID();

            ArrayList<SeatMapSeat> seats = this.svc.getSelectedSeats();
            int[] selectedSeatIds = new int[seats.size()];
            for (int i = 0; i < seats.size(); i++) { // put the seat ids into an array
                selectedSeatIds[i] = seats.get(i).getSeatid();
            }
            try {
                dao.makePurchase(this.getDashboardController().getUser(),
                        this.getEvent(),
                        selectedSeatIds);
                alerts.genericAlert("Tickets have been purchased", Integer.toString(selectedSeatIds.length)
                        + " Tickets have been purchased",
                        Integer.toString(selectedSeatIds.length)
                        + " Tickets have been purchased").showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(PurchasingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Purchasing tickets...userid:" + Long.toString(userid));

            String emailMessage = Messages.purchasedEventMessage(
                    svc.getEvent().getName(),
                    svc.getSelectedSeats(),
                    svc.getPurchaseTotal(),
                    tfFName.getText());
            try {
                SendEmail newEmail = new SendEmail(tfEmail.getText(), "Ticket Purchase", emailMessage, svc.getEvent().getName());
            } catch (MessagingException ex) {
                Logger.getLogger(PurchasingViewController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Mark seat as purchased/unavailable and unload purchasing view
            svc.selectedSeatsPurchased();
            svc.unloadPurchasingView();
        }
    }

    // Set SeatSelectionViewController for callbacks
    public void setSeatSelectionViewController(SeatSelectionViewController seatVC) {
        svc = seatVC;
    }

    // Go back button
    public void goBackToVenue() {
        svc.unloadPurchasingView();
    }

    private void registerCCFieldListener() {
        tfCreditCard.textProperty().addListener((obs, oldText, newText) -> {
            if (tfCreditCard.getText().length() < 2) {
                return;
            }
            if (didCreditCardChangeManual) {
                didCreditCardChangeManual = false;
                return;
            }
            String parsedString = newText.replace("-", "");
            String lastCharNew = newText.substring(newText.length() - 1);
            String lastCharOld = oldText.substring(oldText.length() - 1);

            String appendDash = tfCreditCard.getText();
            appendDash += "-";
            String current = tfCreditCard.getText();

            // Limit to 19 total characters (including dashes)
            if (newText.length() > 19) {
                tfCreditCard.setText(oldText);
                return;
            }

            // Add & Remove dashes automatically
            boolean didChange = false;
            if (newText.length() > oldText.length()) {
                String prependDash = current.substring(0, current.length() - 1)
                        + "-" + current.substring(current.length() - 1, current.length());
                if (parsedString.length() % 4 == 0 && !lastCharNew.equals("-") && !lastCharOld.equals("-")
                        && newText.length() < 16) {
                    updateText = appendDash;
                    didChange = true;
                }
                if (parsedString.length() % 4 == 1 && !lastCharNew.equals("-") && !lastCharOld.equals("-")
                        && newText.length() < 16) {
                    // Prepend dash
                    updateText = prependDash;
                    didChange = true;
                }
            } else {
                if (parsedString.length() % 4 == 0 && lastCharNew.equals("-")) {
                    String removeDash = current.substring(0, tfCreditCard.getText().length() - 1);
                    updateText = removeDash;
                    didChange = true;
                }
            }

            if (didChange) {
                didCreditCardChangeManual = true;
                Platform.runLater(() -> {
                    tfCreditCard.setText(updateText);
                    tfCreditCard.end();
                    updateText = "";
                });
            }
        });
    }
}
