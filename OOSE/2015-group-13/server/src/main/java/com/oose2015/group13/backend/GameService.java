package com.oose2015.group13.backend;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * GameService acts as an interface between the GameController and all the functions related to the game
 */
public class GameService {
    String DataSource;

    /**
     * This is the constructor for the GameService and initializes/opens the database
     * @param DataSource
     */
    public GameService(String DataSource)
    {

    }

    /**
     * This function is called when a new game is created by the user.
     * @param userID
     * @return Game
     */
    public Game newGame(String userID)
    {
        return null;
    }

    /**
     * This function returns the result of the last move,
     * values are taken from an enum and can either be HIT,SINK,MISS
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
}

