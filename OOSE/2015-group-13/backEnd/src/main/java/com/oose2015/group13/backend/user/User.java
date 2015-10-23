package com.oose2015.group13.backend.user;
import java.util.ArrayList;
import java.util.List;

import com.oose2015.group13.backend.gamelogic.gameplayer.Player;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * User class temporarily saves information about the current user
 */
public class User {
    String userID;
    Settings settings;
    List<Player> players;
    int credits;

    /**
     * @param credits Credits accumulated by user in all games played
     */
    public void addCredits(int credits) {
        this.credits += credits;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<Player> getPlayers() {
        if(players == null)
            players = new ArrayList<>();
        
        return players;
    }
    
    public void addPlayer(Player player) {
        getPlayers().add(player);
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    
}
