package io.pinocc.pinocico.java;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
