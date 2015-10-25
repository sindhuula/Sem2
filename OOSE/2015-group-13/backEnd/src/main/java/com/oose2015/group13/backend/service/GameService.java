package com.oose2015.group13.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.oose2015.group13.backend.datalayer.GameDAO;
import com.oose2015.group13.backend.datalayer.UserDAO;
import com.oose2015.group13.backend.gamelogic.game.Game;
import com.oose2015.group13.backend.gamelogic.gameboard.Result;
import com.oose2015.group13.backend.gamelogic.gameplayer.AI;
import com.oose2015.group13.backend.gamelogic.gameplayer.HumanPlayer;
import com.oose2015.group13.backend.gamelogic.gameplayer.Player;
import com.oose2015.group13.backend.user.User;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * GameService acts as an interface between the GameController and all the functions related to the game
 */
public class GameService extends Service<Game> {
    private GameDAO gameDAO;
    private UserDAO userDAO;
    
    //TODO - read from database
    private Map<String, Game> games = new HashMap<>();
    
    public GameService(GameDAO gameDAO, UserDAO userDAO) {
        super();
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
    }

    /**
     * This function returns the result of the last move,
     * values are taken from an enum and can either be HIT,SINK,MISS
     * @param fromPlayerID ID of player making the movie
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
     * @param userID ID of player joining the game
     * @param gameType Specifies whether game is single/multi player
     * @param boardLayout Selected board layout of the user
     * @return Game
     */
    public Game joinGame(String userID, String gameType, String boardLayout) {
        return null;
    }

    @Override
    public Game create() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Game createFromJSON(String json) {
        JSONObject jsonObj = new JSONObject(json);
        
        //TODO - UserNotFoundException(), throw GameNotCreatedException()?
        //check user not exists
        User user = userDAO.find(jsonObj.getString("userID"));
        Player player = new HumanPlayer(user);
        
        //Add player to User's list of players
        user.addPlayer(player);
        
        //TODO - game init with settings
        Game game = new Game(player);
        String dummyID = String.valueOf(games.size());
        game.setGameID(dummyID);
        games.put(dummyID, game);

        //if it's a single player game, challengedUserIDs will be an empty array.
        //if length of the array is not 0, add the challenged Users to game
        if(jsonObj.getJSONArray("challengedUserIDs").length() == 0) {
            //Start a game with AI
            game.playerJoined(new AI(), true);
        } else {
            //Start a game with the other players
            for(Object userID : jsonObj.getJSONArray("challengedUserIDs")) {
                String id = String.valueOf(userID);
                User challengedUser = userDAO.find(id);
                Player challengedPlayer = new HumanPlayer(challengedUser);
                //Update user's record to reflect the new challenge
                userDAO.update(id,user.getUserID(),dummyID);
                //Add player to user's list of players, so user can get to their games
                challengedUser.addPlayer(challengedPlayer);
                game.playerJoined(challengedPlayer, false);
            } 
        }

        return game;
    }

    @Override
    public Game update(String id, String body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Game find(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }
}

