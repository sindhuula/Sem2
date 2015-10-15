package com.oose2015.group13.backend;
import java.util.Map;
import java.util.List;
import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by OOSE Group 13 on 10/11/2015.
*/
/**
 * Game describes the current game like the game state, who all are playing, whose turn it is etc.
 */
public class Game {
    String gameID;
    Status state;
    Player playerTurn;
    Map<Player,Boolean> playersJoined;
    List<Player> players;
    Board boardTiles;

    /**
     * This function adds a new player to the game
     * @param add details of player who's joining
     * @return void
     */
    public void addPlayerJoined(Player add)
    {

    }

    /**
     * This function checks if all the players have joined the game. This comes in handy in multi-player mode
     * @param gameID
     * @return Boolean
     */
    public Boolean allPlayersJoined(String gameID)
    {
        return null;
    }

    /**
     * This function returns the result of the last move,
     * values are taken from an enum and can either be HIT_SHIP,HIT_LAND,SINK,MISS or ALREADY_ATTACKED
     * @param fromPlayerID
     * @param toPlayerID
     * @param x
     * @param y
     * @return Result
     */
    public Result move(String fromPlayerID, String toPlayerID,Integer x, Integer y)
    {
        return null;
    }

    /**
     * This function returns the game value to the new user who's joining the game
     * @param userID
     * @param gameType
     * @param boardLayout
     * @return Game
     */
    public Game joinGame(String userID, String gameType, String boardLayout)
    {
        return null;
    }

    /**
     * This function fetches the current board contents(when initially the game starts it would about each tile on the board)
     * @param boardLayout
     * @return Board
     */
    private Board fetchBoardTiles(String boardLayout)
    {
        return null;
    }
}
