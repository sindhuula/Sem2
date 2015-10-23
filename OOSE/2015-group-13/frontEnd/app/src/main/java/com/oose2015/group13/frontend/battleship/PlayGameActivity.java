package com.oose2015.group13.frontend.battleship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * This is the "main" activity in which a user plays the game.  This activity makes calls to
 * the server retrieve the board, the game state, request to make a move, and to place a ship.
 * This activity maintains game state, and refetches is every time the activity is create.
 */

public class PlayGameActivity extends AppCompatActivity {

    /**
     * These data members represent the game state in this activity.
     */
    public String gameState;
    public Map<String, ArrayList<Integer>> piecePositions;
    public ArrayList<ArrayList<Integer>> firedMissed;
    public ArrayList<ArrayList<Integer>> firedHit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
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
     * Checks to see whether a particular placement of ship is valid.  This method makes an
     * HTML API call back to the server to compute whether it is valid.  If it is not valid,
     * user must continue to place ships.
     * @param pieceID           identifies which chiece is moving
     * @param piecePositions    2D array of integers representing the pieces location
     * @return                  whether the placement is valid
     */
    public boolean placeShip(String pieceID, ArrayList<Integer> piecePositions){

        return true;
    }

    /**
     * Sends to server that a user in a game has placed his ships and is ready to play.  This
     * method is not called until all of the user's ships have been placed in a valid position.
     * @param userID            Identifies user to server
     * @param gameID            Identifies game to server
     */
    public void sendReady(String userID, String gameID){

        return;
    }

    /**
     * Sends to server a move to make a move on the board.  Returns false if move is invalid.
     * If not valid, user will loop in make-a-move.
     * @param userID            Identifies user to server
     * @param gameID            Identifies game to server
     * @param toX               Identifies x coordinate of move/shot
     * @param toY               Identifies y coordinate of move/shot
     * @return                  whether the move is valid
     */
    public boolean makeMove(String userID, String gameID, int toX, int toY){

        return true;
    }

    /**
     * This method is called once every second to fetch the most current game state.
     * @param userID            Identifies user to server
     * @param gameID            Identifies game to server
     */
    public void updateGameState(String userID, String gameID){

        return;
    }

    /**
     * This method is called once, when the activity is created, to fetch the board piece positions
     * from the server.
     * @param userID            Identifies user to server
     * @param gameID            Identifies game to server
     */
    public void getGameBoard(String gameID, String userID){

        return;
    }

}
