package io.pinocc.pinocico.java;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;


/**
 * Created by Haifisch on 7/29/14.
 */
public class PinoccioAPI {
    public void turnLEDOn(int scoutID, int troopID, String token) {
        requestAPI(String.format("https://api.pinocc.io/v1/%s/%s/command/led.on?token=%s", scoutID, troopID, token));
    }

    public void turnLEDOff(int scoutID, int troopID, String token) {
        requestAPI(String.format("https://api.pinocc.io/v1/%s/%s/command/led.off?token=%s",scoutID,troopID,token));
    }
    public JsonArray troopsInAccount(String token) {

        JsonElement jElement = new JsonParser().parse(requestAPI(String.format("https://api.pinocc.io/v1/troops?token=%s",token)));
        JsonObject  jObject = jElement.getAsJsonObject();
        JsonArray jAry = jObject.getAsJsonArray("data");
        return jAry;
    }
    public JsonArray scoutsInTroop(int troopID, String token) {

        JsonElement jElement = new JsonParser().parse(requestAPI(String.format("https://api.pinocc.io/v1/%s/scouts?token=%s",troopID,token)));
        JsonObject  jObject = jElement.getAsJsonObject();
        JsonArray jAry = jObject.getAsJsonArray("data");
        return jAry;

    }
    public JsonObject runBitlashCommand(int troopID, int scoutID, String command, String token){
        String url = null;
        try {
             url = URLEncoder.encode(command, "UTF-8");
        }catch (Exception exc){

        }
        JsonElement jElement = new JsonParser().parse(requestAPI(String.format("https://api.pinocc.io/v1/%s/%s/command/%s?token=%s",troopID,scoutID,url,token)));
        JsonObject  jObject = jElement.getAsJsonObject().get("data").getAsJsonObject();
        return jObject;
    }
    public JsonObject loginWithCredentials(String email, String password){
        final String server = "api.pinocc.io";

        URL url = null;
        try {
            url = new URL("https://" + server + "/v1/login");
        } catch (MalformedURLException ex) {
            System.out.println(ex); 
        }

        HttpURLConnection urlConn = null;
        try {
            // URL connection channel.
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);

        // Let the RTS know that we want to do output.
        urlConn.setDoOutput (true);

        // No caching, we want the real thing.
        urlConn.setUseCaches (false);

        try {
            urlConn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            System.out.println(ex); 
        }

        try {
            urlConn.connect();
        } catch (IOException ex) {
            System.out.println(ex); 
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex); 
        }

        // Specify the content type if needed.
        //urlConn.setRequestProperty("Content-Type",
        //  "application/x-www-form-urlencoded");

        // Construct the POST data
        String content = null;
        try {
            content = "email=" + URLEncoder.encode(email,"UTF-8") + "&password=" + URLEncoder.encode(password,"UTF-8");
        }catch (Exception exc){
            System.out.println(exc);
        }

        // Send the request data.
        try {
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {
            System.out.println(ex); 
        }

        // Get response data.
        String str = null;
        try {
            input = new DataInputStream (urlConn.getInputStream());
            while (null != ((str = input.readLine()))) {
                JsonElement jElement = new JsonParser().parse(str);
                JsonObject  jObject = jElement.getAsJsonObject().get("data").getAsJsonObject();
                return jObject;
            }
            input.close ();
        } catch (IOException ex) {
            System.out.println(ex); 
        }


        return null;
    }
    public Boolean logoutWithSession(String token){
        final String server = "api.pinocc.io";

        URL url = null;
        try {
            url = new URL("https://" + server + "/v1/logout");
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        }

        HttpURLConnection urlConn = null;
        try {
            // URL connection channel.
            urlConn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Let the run-time system (RTS) know that we want input.
        urlConn.setDoInput (true);

        // Let the RTS know that we want to do output.
        urlConn.setDoOutput (true);

        // No caching, we want the real thing.
        urlConn.setUseCaches (false);

        try {
            urlConn.setRequestMethod("POST");
        } catch (ProtocolException ex) {
            System.out.println(ex);
        }

        try {
            urlConn.connect();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        DataOutputStream output = null;
        DataInputStream input = null;

        try {
            output = new DataOutputStream(urlConn.getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Specify the content type if needed.
        //urlConn.setRequestProperty("Content-Type",
        //  "application/x-www-form-urlencoded");

        // Construct the POST data
        String content = null;
        try {
            content = "token=" + URLEncoder.encode(token,"UTF-8");
        }catch (Exception exc){
            System.out.println(exc);
        }

        // Send the request data.
        try {
            output.writeBytes(content);
            output.flush();
            output.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Get response data.
        String str = null;
        try {
            input = new DataInputStream (urlConn.getInputStream());
            while (null != ((str = input.readLine()))) {
                JsonElement jElement = new JsonParser().parse(str);
                Boolean  jObject = jElement.getAsJsonObject().get("data").getAsBoolean();

                return jObject;
            }
            input.close ();
        } catch (IOException ex) {
            System.out.println(ex);
        }


        return null;
    }
    private String requestAPI(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";

        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }



}
