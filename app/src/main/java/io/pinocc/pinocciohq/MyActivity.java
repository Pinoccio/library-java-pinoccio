package io.pinocc.pinocciohq;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.pinocc.pinocico.java.*;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;


public class MyActivity extends Activity {

    final PinoccioAPI pinoccioAPI = new PinoccioAPI();
    Boolean isRunning = Boolean.FALSE;
    int troopID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (!isRunning) {
            isRunning = Boolean.TRUE;
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        final JsonObject objobj= pinoccioAPI.loginWithCredentials("dylan@pinocc.io","");
                        System.out.println(objobj);

                        final JsonObject obj = pinoccioAPI.troopsInAccount(objobj.get("token").getAsString()).get(0).getAsJsonObject();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                troopID = obj.get("id").getAsInt();
                                Thread thread = new Thread(new Runnable(){
                                    @Override
                                    public void run() {
                                        try {
                                            //final JsonObject objobj= pinoccioAPI.loginWithCredentials("dylan@pinocc.io","Dman6049");
                                            //System.out.println(objobj);

                                            final boolean obj = pinoccioAPI.logoutWithSession(objobj.get("token").getAsString());
                                            System.out.println(obj);

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //((TextView) findViewById(R.id.textView)).setText(obj.get("name").getAsString());
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                thread.start();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllData(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    final JsonObject obj = pinoccioAPI.troopsInAccount("q4vn2g1smdcmb5efuco1eoj184").get(0).getAsJsonObject();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.textView2)).setText(obj.get("name").getAsString());
                            troopID = obj.get("id").getAsInt();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        System.out.println(troopID);

        thread = null;
        thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    final JsonArray obj = pinoccioAPI.scoutsInTroop(troopID,"q4vn2g1smdcmb5efuco1eoj184").getAsJsonArray();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //((TextView) findViewById(R.id.textView2)).setText(obj.get("name").getAsString());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    boolean onOff;
    int TROOP_ID_INT = 4;
    int SCOUT_ID_INT = 2;
    String SESSION_TOKEN_STRING = "q4vn2g1smdcmb5efuco1eoj184";
    public void turnLEDOn(View viw) throws IOException {
        final PinoccioAPI pinoccioAPI = new PinoccioAPI();
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    onOff = ((ToggleButton) findViewById(R.id.togglebutton)).isChecked();
                    if (onOff) {
                        pinoccioAPI.turnLEDOn(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING);
                    }else {
                        pinoccioAPI.turnLEDOff(TROOP_ID_INT, SCOUT_ID_INT, SESSION_TOKEN_STRING);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start(); // Threading is a fucking mess here, but it's quick and simple. AsyncTask was taking forever to execute.
    }
}
