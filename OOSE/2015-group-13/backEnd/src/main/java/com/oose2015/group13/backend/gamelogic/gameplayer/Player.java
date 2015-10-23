package com.oose2015.group13.backend.gamelogic.gameplayer;

import com.oose2015.group13.backend.gamelogic.game.Game;
import com.oose2015.group13.backend.user.User;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 *  Player class saves the details of the players of a game. AI class inherits from this
 */
public abstract class Player {
    protected Game game;
    protected User parent;
    protected String playerID;
    
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }
    
}
