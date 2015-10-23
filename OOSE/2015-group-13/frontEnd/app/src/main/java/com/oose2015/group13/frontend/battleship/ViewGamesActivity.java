package com.oose2015.group13.frontend.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * In this activity, user views and manages current games with which the user
 * is associated.  The method retrieveGames quiries the server for associated games
 * and their status.  This activity launches the CreateNewGame Activity and the
 * PlayGame Activity.  This activity is queued as the most recently run activity while
 * a user is playing a game, and will be resumed when a player ends the PlayGame activity.
 */

public class ViewGamesActivity extends AppCompatActivity {


    /**
     * This data member holds the relevant data for games with which the player is associated.
     */
    public ArrayList<Map<Integer, String>> GamesAndStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_games);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_games, menu);
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
     * This method is called once every five seconds.  It makes a call back to the server
     * with the user's userID and recieves all games in which this user is involved, either
     * as a current player or a requested player.  This information is represented as a string,
     * which is later input into a transformer which creates the GamesAndStatus data member.
     * @param userID            Identifies userID to server
     * @return                  gamesStatus in form of string
     */
    public String retrieveGames(String userID){

        return "";
    }

    /**
     * Launches CreateGame Activity, passing the userID string in through an intent
     * @param userID            Identifies userID to server
     */
    public void createGame(String userID){
        // launch CreateGameActivity
        return;
    }

    /**
     * Launches PlayGameActivity, passing the user's userID and gameID through an intent
     * @param userID            Identifies user to server
     * @param gameID            Identifies game to server
     */
    public void launchGame(String userID, String gameID){
        // launch PlayGameActivity
        return;
    }
}
