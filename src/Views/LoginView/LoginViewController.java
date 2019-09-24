/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.LoginView;

import Classes.APIs.TicketMaster.TicketMasterEvent;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginViewController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         try{
            String webService = "https://app.ticketmaster.com/discovery/v2/events.json?classificationName=baseball&dmaId=324&apikey=2uhGCartHuAyB1iNQZe2vfeVAFtaXlSm";
            URL apiURL = new URL(webService);
            HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() ==200)
            {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                
                String output;
                JSONObject json = null;
                
                // Read the output into a json object for safe keeping.
                while ((output = br.readLine()) != null)
                {
                    json = new JSONObject(output);
                }
                // convert the json object to a string
                output = json.toString();
                
                // Deserialize the json string into a Java object.
                System.out.println(output);
                Gson gson = new Gson();
                TicketMasterEvent tme = gson.fromJson(output, TicketMasterEvent.class);
                System.out.println("stuff");

            }
        }
        catch (IOException e) {
            System.out.println(e);
        } catch (JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
    }    
    
}
