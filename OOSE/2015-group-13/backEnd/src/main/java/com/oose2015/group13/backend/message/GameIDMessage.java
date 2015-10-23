package com.oose2015.group13.backend.message;

import com.oose2015.group13.backend.gamelogic.game.Game;

public class GameIDMessage {
    private String gameID;
    public GameIDMessage(Game game) {
        this.gameID = game.getGameID();
    }
    public String getGameID() {
        return gameID;
    }
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
