package com.oose2015.group13.backend.controller;

import static spark.Spark.post;

import java.util.Properties;

import com.oose2015.group13.backend.service.UserService;
import com.oose2015.group13.backend.transformer.JsonTransformer;
import com.oose2015.group13.backend.user.User;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * UserController controls the UserService and handles User related requests.
 * Subclass of Controller, and parameterized with User
 */
public class UserController extends Controller {
    private UserService service;
    
    public UserController(UserService service, Properties properties) {
        super(properties);
        this.service = service;
    }

    /**
     * This function sets up API endpoints for the User actions.
     * It acts as an interface between the frontend and the GameService for a game
     */
    @Override
    public void setupEndpoints() {
        //Login as guest
        post(context + "/auth/login", "application/json", (req, res) -> {
            logger.info("Running API method /auth/login");
            User user = service.create();
            res.status(201);
            return user;
        }, new JsonTransformer());
        
        //Login with Google+
        post(context + "/auth/login/:id", "application/json", (req, res) -> {
            logger.info("Running API method /auth/login/" + req.params(":id"));
            
            //Try and find user in database (will return null if not found)
            User user = service.find(req.params(":id"));
            
            if(user == null) {
                logger.info("User not found in database: " + req.body());
                logger.info("Creating user");
                
                //Create new user, set response status
                user = service.createWithID(req.params(":id"));
                res.status(201);
                
            } else {
                res.status(200);
                logger.info("User found in database with id: " + user.getUserID());
            }
            
            return user;
        }, new JsonTransformer());
    }
}
