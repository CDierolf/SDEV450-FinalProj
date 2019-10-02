package Classes.APIs.TicketMaster;

import Views.LoginView.LoginViewController;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Course: SDEV 350 ~ Java Programming II
 * @Author Name: Christopher K. Dierolf
 * @Assignment Name: Classes.APIs.TicketMaster
 * @Date: Sep 24, 2019
 * @Subclass TicketMasterAPI Description:
 */
//Imports
//Begin Subclass TicketMasterAPI
public class TicketMasterAPI {

    private final String API_KEY = "2uhGCartHuAyB1iNQZe2vfeVAFtaXlSm";
    private final String API_BASE_URL = "https://app.ticketmaster.com/discovery/v2/";
    private final int HARDCODED_REGION = 90017;

    public void getTicketMasterJSONEventData(String eventCategory) {
        
        HttpURLConnection connection;
        JSONObject ticketMasterJsonObject = null;
        String ticketMasterJsonString;
        BufferedReader ticketMasterJsonStream;
        TicketMasterEvent ticketMasterEvent;

        try {
            connection = createTicketMasterAPIConnection();
            if (checkTicketMasterAPIConnection(connection)) {

                ticketMasterJsonStream = getTicketMasterJSONStream(connection);
                ticketMasterJsonObject = parseTicketMasterJSONStreamIntoObject(ticketMasterJsonStream);
                ticketMasterJsonString = ticketMasterJsonObject.toString();
                ticketMasterEvent = deserializeTicketMasterJsonIntoEventObject(ticketMasterJsonString);
                System.out.println(ticketMasterEvent);

            } else {
                // TODO Throw error
                // Unable to connect to api
                System.out.println("Unable to connect to api.");
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private HttpURLConnection createTicketMasterAPIConnection() throws MalformedURLException, ProtocolException, IOException {
        
        // TODO Create string formatter to add the classificationName for the users 
        // entered event category with parameters of classificationName and apikey
        String webService = "https://app.ticketmaster.com/discovery/v2/events.json?classificationName=music&&dmaId=324&apikey=2uhGCartHuAyB1iNQZe2vfeVAFtaXlSm";
        URL apiURL = new URL(webService);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return connection;
    }

    private boolean checkTicketMasterAPIConnection(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() == 200;
    }

    private BufferedReader getTicketMasterJSONStream(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedTicketMasterStream = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        return bufferedTicketMasterStream;
    }

    private JSONObject parseTicketMasterJSONStreamIntoObject(BufferedReader jsonStream) throws IOException, JSONException {
        JSONObject ticketMasterJsonObject = null;
        String ticketMasterOutput;
        while ((ticketMasterOutput = jsonStream.readLine()) != null) {
            ticketMasterJsonObject = new JSONObject(ticketMasterOutput);
        }

        if (ticketMasterJsonObject != null) {
            return ticketMasterJsonObject;
        } else {
            return null;
        }
    }

    private TicketMasterEvent deserializeTicketMasterJsonIntoEventObject(String ticketMasterJsonString) {
        Gson ticketMasterGsonObject = new Gson();
        TicketMasterEvent tme = ticketMasterGsonObject.fromJson(ticketMasterJsonString, TicketMasterEvent.class);
        
        return tme;
    }

} //End Subclass TicketMasterAPI
