package Classes.APIs.TicketMaster;

import Views.LoginView.LoginViewController;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private final String API_BASE_URL = "https://app.ticketmaster.com/discovery/v2/events?size=20";



    public TicketMasterEvent findEvents(String eventKeyword, String pageNumber, String postalCode) {
        TicketMasterEvent event = getTicketMasterJSONEventData(eventKeyword, pageNumber, postalCode);

        if (event.getEmbeddedEvents() == null) {
            System.out.println("No events found.");
            // No events were found :(
            return null;
        } else {
            return (event);
        }
    }

    public TicketMasterEvent findEvents(String eventID) {
        TicketMasterEvent event = getTicketMasterJSONEventData(eventID);
        if (event.getEmbeddedEvents() == null) {
            System.out.println("No events found.");
            // No events were found :(
            return null;
        } else {
            return (event);
        }
    }

    private TicketMasterEvent getTicketMasterJSONEventData(String eventKeyword, String pageNumber, String postalCode) {

        HttpURLConnection connection;
        JSONObject ticketMasterJsonObject = null;
        String ticketMasterJsonString;
        BufferedReader ticketMasterJsonStream;
        TicketMasterEvent ticketMasterEvent;
        try {
            connection = createTicketMasterAPIConnection(eventKeyword, pageNumber, postalCode);
            if (checkTicketMasterAPIConnection(connection)) {

                ticketMasterJsonStream = getTicketMasterJSONStream(connection);
                ticketMasterJsonObject = parseTicketMasterJSONStreamIntoObject(ticketMasterJsonStream);
                ticketMasterJsonString = ticketMasterJsonObject.toString();
                ticketMasterEvent = deserializeTicketMasterJsonIntoEventObject(ticketMasterJsonString);
                return ticketMasterEvent;

            } else {
                // TODO Throw error
                // Unable to connect to api
                System.out.println("Unable to connect to api.");
                System.out.println(connection.getResponseCode());

                return null;
            }
        } catch (IOException e) {
            System.out.println(e);
            return null;
        } catch (JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private TicketMasterEvent getTicketMasterJSONEventData(String eventId) {
        HttpURLConnection connection;
        JSONObject ticketMasterJsonObject = null;
        String ticketMasterJsonString;
        BufferedReader ticketMasterJsonStream;
        TicketMasterEvent ticketMasterEvent;
        try {
            connection = createTicketMasterAPIConnection(eventId);
            if (checkTicketMasterAPIConnection(connection)) {

                ticketMasterJsonStream = getTicketMasterJSONStream(connection);
                ticketMasterJsonObject = parseTicketMasterJSONStreamIntoObject(ticketMasterJsonStream);
                ticketMasterJsonString = ticketMasterJsonObject.toString();
                ticketMasterEvent = deserializeTicketMasterJsonIntoEventObject(ticketMasterJsonString);
                return ticketMasterEvent;

            } else {
                // TODO Throw error
                // Unable to connect to api
                System.out.println("Unable to connect to api.");
                System.out.println(connection.getResponseCode());

                return null;
            }
        } catch (IOException e) {
            System.out.println(e);
            return null;
        } catch (JSONException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private HttpURLConnection createTicketMasterAPIConnection(String eventKeyword, String pageNumber, String postalCode) throws ProtocolException, IOException {

        URL apiURL = encodeUrl(eventKeyword, pageNumber, postalCode);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return connection;
    }

    // Get HTTP API Connection
    private HttpURLConnection createTicketMasterAPIConnection(String eventId) throws ProtocolException, IOException {
        URL apiURL = encodeUrl(eventId);
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        return connection;
    }

    // Check connection
    private boolean checkTicketMasterAPIConnection(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() == 200;
    }

    // Get JSON Stream
    private BufferedReader getTicketMasterJSONStream(HttpURLConnection connection) throws IOException {
        BufferedReader bufferedTicketMasterStream = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        return bufferedTicketMasterStream;
    }

    // Parse JSON stream into an event object
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

    // Deserialize JSON into the Event object
    private TicketMasterEvent deserializeTicketMasterJsonIntoEventObject(String ticketMasterJsonString) {

        Gson ticketMasterGsonObject = new Gson();
        TicketMasterEvent tme = ticketMasterGsonObject.fromJson(ticketMasterJsonString, TicketMasterEvent.class);

        return tme;
    }

    // Encode url for event
    private URL encodeUrl(String keyWord, String pageNum, String postalCode) throws MalformedURLException, UnsupportedEncodingException {

        String query = "&countryCode=US&page=" + pageNum + "&apikey=" + API_KEY + "&postalCode=" + postalCode + "&keyword="
                + URLEncoder.encode(keyWord, StandardCharsets.UTF_8.toString()) + "&locale=en-us";
        URL url;
        url = new URL(API_BASE_URL + query);
        return url;
    }

    // Encode url for eventid
    private URL encodeUrl(String eventId) throws MalformedURLException {
        String query = "&eventID=" + eventId + "&apikey=" + API_KEY;
        System.out.println("QUERY" + query);
        URL url;
        url = new URL(API_BASE_URL + query);
        return url;

    }

} //End Subclass TicketMasterAPI
