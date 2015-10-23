package com.oose2015.group13.backend.message;

import java.util.HashSet;
import java.util.Set;

import com.oose2015.group13.backend.gamelogic.game.Game;
import com.oose2015.group13.backend.gamelogic.gameplayer.Player;
import com.oose2015.group13.backend.user.User;

public class GamesMessage {
    Set<Game> games;
    
    public GamesMessage(User user) {
        for(Player player : user.getPlayers()) {
            addGame(player.getGame());
        }
    }
    
    public void addGame(Game game) {
        if(games == null) {
            games = new HashSet<>();
        }
        
        games.add(game);
    }
}
