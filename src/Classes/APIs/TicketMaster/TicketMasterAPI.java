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
    private final int HARDCODED_REGION = 90017;
    
    private String eventKeyword;
    private String pageNumber;
    private String postalCode;
    
    public TicketMasterAPI() {}
    public TicketMasterAPI(String eventKeyword, String pageNumber) {
        this.eventKeyword = eventKeyword;
        this.pageNumber = pageNumber;
    }
    public TicketMasterAPI(String eventKeyword, String postalCode, String pageNumber) {
        this.eventKeyword = eventKeyword;
        this.postalCode = postalCode;
        this.pageNumber = pageNumber;
    }
    
    public TicketMasterEvent findEvents(String eventKeyword, String pageNumber, String postalCode) {
        TicketMasterEvent event = getTicketMasterJSONEventData(eventKeyword, pageNumber, postalCode);
        
        System.out.println("NUMBER OF EVENTS: " + event.getEmbeddedEvents().getNumberOfEvents());
        
        System.out.println(event);
        if (event == null) {
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

                return ticketMasterEvent; // Everything we alright.
                
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

    //TODO
    // Implement RADIUS
    // GET VENUE INFORMATION https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/#venue-details-v2
    private HttpURLConnection createTicketMasterAPIConnection(String keyword, String pageNumber, String postalCode) throws ProtocolException, IOException {

        //String webService = "https://app.ticketmaster.com/discovery/v2/events?apikey=2uhGCartHuAyB1iNQZe2vfeVAFtaXlSm&keyword="+keyword+"&page="+pageNumber+"&locale=*";
        //String webService = "https://app.ticketmaster.com/discovery/v2/events?size=20&page="+pageNumber+"&apikey=2uhGCartHuAyB1iNQZe2vfeVAFtaXlSm&keyword="+keyword+"&locale=*";

        //URL apiURL = new URL(webService);
        URL apiURL = encodeUrl(keyword, pageNumber, postalCode);
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
    
    private URL encodeUrl(String keyWord, String pageNum, String postalCode) throws MalformedURLException, UnsupportedEncodingException {

        String query = "&countryCode=US&page="+pageNum+"&apikey="+API_KEY+"&postalCode="+postalCode+"&keyword="+ 
                URLEncoder.encode(keyWord, StandardCharsets.UTF_8.toString())+"&locale=en-us";
        URL url;
        url = new URL(API_BASE_URL + query);
        return url;
    }

} //End Subclass TicketMasterAPI
