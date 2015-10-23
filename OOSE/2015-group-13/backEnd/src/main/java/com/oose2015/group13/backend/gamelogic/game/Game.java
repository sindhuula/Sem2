package com.oose2015.group13.backend.gamelogic.game;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oose2015.group13.backend.gamelogic.gameboard.Board;
import com.oose2015.group13.backend.gamelogic.gameboard.Result;
import com.oose2015.group13.backend.gamelogic.gameboard.Status;
import com.oose2015.group13.backend.gamelogic.gameplayer.Player;

/**
 * Created by OOSE Group 13 on 10/11/2015.
*/
/**
 * Game describes the current game like the game state, who all are playing, whose turn it is etc.
 */
public class Game {
    String gameID;
    Status status;
    Player playerTurn;
    Map<Player, Boolean> playersJoined;
    List<Player> players;
    Board boardTiles;
    
    public Game(Player firstPlayer) {
        playerJoined(firstPlayer, true);
        status = Status.WAITING_FOR_PLAYERS;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Map<Player, Boolean> getPlayersJoined() {
        return playersJoined;
    }

    public void setPlayersJoined(Map<Player, Boolean> playersJoined) {
        this.playersJoined = playersJoined;
    }
    
    public void playerJoined(Player player, boolean join) {
        if(playersJoined == null)
            playersJoined = new HashMap<>();
        
        player.setGame(this);
        playersJoined.put(player, join);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Board getBoardTiles() {
        return boardTiles;
    }

    public void setBoardTiles(Board boardTiles) {
        this.boardTiles = boardTiles;
    }

    /**
     * This function adds a new player to the game
     * @param add details of player who's joining
     */
    public void addPlayerJoined(Player add)
    {

    }

    /**
     * This function checks if all the players have joined the game. This comes in handy in multi-player mode
     * @param gameID Unique ID of game being played
     * @return Boolean
     */
    public Boolean allPlayersJoined(String gameID)
    {
        return null;
    }

    /**
     * This function returns the result of the last move,
     * values are taken from an enum and can either be HIT_SHIP,HIT_LAND,SINK,MISS or ALREADY_ATTACKED
     * @param fromPlayerID ID of player making the move
     * @param toPlayerID ID of player being attacked
     * @param x X coordinate of tile being attacked
     * @param y Y coordinate of tile being attacked
     * @return Result
     */
    public Result move(String fromPlayerID, String toPlayerID,Integer x, Integer y)
    {
        return null;
    }

    /**
     * This function returns the game value to the new user who's joining the game
     * @param userID Login ID of player
     * @param gameType Specifies whether the game is single/multi player
     * @param boardLayout Selected board layout of the user
     * @return Game
     */
    //These parameters need to change
    public Game joinGame(String userID, String gameType, String boardLayout)
    {
        return null;
    }

    /**
     * This function fetches the current board contents(when initially the game starts it would about each tile on the board)
     * @param boardLayout Selected board layout of the user
     * @return Board
     */
    private Board fetchBoardTiles(String boardLayout)
    {
        return null;
    }
}
