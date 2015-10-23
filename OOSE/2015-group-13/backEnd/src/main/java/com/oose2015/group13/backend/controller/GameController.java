package com.oose2015.group13.backend.controller;

import static spark.Spark.post;

import java.util.Properties;

import com.oose2015.group13.backend.gamelogic.game.Game;
import com.oose2015.group13.backend.message.GameIDMessage;
import com.oose2015.group13.backend.service.GameService;
import com.oose2015.group13.backend.transformer.JsonTransformer;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * GameController controls the Game Service and handles Game related requests
 */
public class GameController extends Controller {
    private GameService service;
    
    public GameController(GameService service, Properties properties) {
        super(properties);
        this.service = service;
    }

    /**
     * This function sets up API endpoints for the Game actions.
     * It acts as an interface between the frontend and the GameService for a game
     */
    @Override
    public void setupEndpoints() {
        post(context + "/games/new", (req, res) -> {
            logger.info("Running API method: /games/new");
            Game game = service.createFromJSON(req.body());
            return new GameIDMessage(game);
        }, new JsonTransformer());
    }
}
