package com.oose2015.group13.frontend.battleship;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * In this activity, the user logs into google+ to confirm their unique user ID, or continues
 * as guest (String userID = "guest").  If the user clicks "log in using google+," the activity
 * will loop until the user either enters a valid google+ ID and password and is confirmed
 * throught he google+ API, or continues as guest.
 */

public class LoginActivity extends AppCompatActivity {

    String userID = "";
    String userPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);



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

    /**
     * Makes call using google+ plugin to confirm a unique userID.
     * @param userID
     * @return
     */
    boolean googlePlusLogin(String userID){
        //call googleLogin

        return true;
    }

    /**
     * Makes call to server using string userID and returns associated userID settings
     * Then enstantiates a user settings object to pass back to the StartActivity
     * @param
     * @return
     */
    public void serverLogin(View view){

        EditText editText = (EditText) findViewById(R.id.enterUserID);
        userID = editText.getText().toString();
        editText = (EditText) findViewById(R.id.enterUserID);
        userPassword = editText.getText().toString();

        //transformt to JSON

        //send to JSON


        //TODO: call google+ api
        //TODO: login with server
        //TODO: get data back, put in intend

        Intent viewGamesIntent = new Intent(this, ViewGamesActivity.class);
        startActivity(viewGamesIntent);

    }

    class DoGetRequest extends AsyncTask<String, Void, String> {

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

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}
