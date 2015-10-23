package com.oose2015.group13.frontend.battleship;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is the first activity that starts when the app is launched.  It
 * immediately creates the LoginActivity and receives back from it
 * a userSettings object (serialized), and then creates and launches the viewGamesActivity.
 * After starting the viewGamesActivity, this activity stops.
 *
 * @see
 */


public class StartActivity extends AppCompatActivity {


    String urlString = "http://10.0.2.2:8080/hareandhounds/api/games/1/board";
    //String urlString ="http://www.android.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImageView img = (ImageView) findViewById(R.id.animationImage);
        final AnimationDrawable myAnimationDrawable
                = (AnimationDrawable)img.getDrawable();

        img.post(
                new Runnable(){

                    @Override
                    public void run() {
                        myAnimationDrawable.start();
                    }
                });

    }

    public void startLoginActivity(View view){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }


    class DoGetRequest extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            String urlString = params[0]; // URL to call
            String result = "";

            // HTTP Get
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                if (null != inputStream)
                    result = readStream(inputStream);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView serverResponse = (TextView) findViewById(R.id.thisID);
            serverResponse.setText("server Response = " + result);
        }
    }

}
