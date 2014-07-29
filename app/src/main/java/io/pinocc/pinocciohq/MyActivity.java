package io.pinocc.pinocciohq;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;
import io.pinocc.pinocico.java.*;
import org.json.JSONObject;

import java.io.IOException;


public class MyActivity extends Activity {
    private static  String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
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
    boolean onOff;
    public void turnLEDOn(View viw) throws IOException {
        final PinoccioAPI pinoccioAPI = new PinoccioAPI();
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    pinoccioAPI.troopsInAccount("q4vn2g1smdcmb5efuco1eoj184");
                    onOff = ((ToggleButton) findViewById(R.id.togglebutton)).isChecked();
                    if (onOff) {
                        pinoccioAPI.turnLEDOn(4, 2, "q4vn2g1smdcmb5efuco1eoj184");
                    }else {
                        pinoccioAPI.turnLEDOff(4, 2, "q4vn2g1smdcmb5efuco1eoj184");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start(); // Threading is a fucking mess here, but it's quick and simple. AsyncTask was taking forever to execute.
    }
}
