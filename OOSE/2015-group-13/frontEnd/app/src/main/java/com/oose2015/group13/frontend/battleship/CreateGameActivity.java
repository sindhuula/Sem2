package com.oose2015.group13.frontend.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


/**
 * In this activity, a user can create a new game and send a request to play with other users or
 * against an AI.
 */

public class CreateGameActivity extends AppCompatActivity {

    public String userID;
    public String gameID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_game, menu);
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
     * Called when user clicks to play against AI; sends request to server to create game with
     * this userID and an AI
     * @param userID            Identifies user to server
     */
    public void inviteAI(String userID, String gameID){

        return;
    }


    /**
     * Sends request to server to invite other users to join current game.
     * @param userID                Identifies user to server
     * @param gameID                Identifies game to server
     * @param inviteUser            ArrayList of strings representing userIDs to invite
     */
    public void inviteFriends(String userID, String gameID, ArrayList<String> inviteUser){


        return;
    }

}
