package com.oose2015.group13.backend.service;

import java.util.HashMap;
import java.util.Map;

import com.oose2015.group13.backend.datalayer.GameDAO;
import com.oose2015.group13.backend.datalayer.UserDAO;
import com.oose2015.group13.backend.user.Settings;
import com.oose2015.group13.backend.user.User;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * UserService acts as an interface between UserController and functions for user
 */
public class UserService extends Service<User> {
    //Dummy Set of all Users in memory
    //TODO - put this in database
    private Map<String, User> users = new HashMap<>();
    private UserDAO userDAO;
    private GameDAO gameDAO;
    
    public UserService(UserDAO userDAO, GameDAO gameDAO) {
        super();
        this.userDAO = userDAO;
        this.gameDAO = gameDAO;
    }
    
    /**
     * This function signs in the user and retrieves their data from the database
     * @param userID ID given by user in login box
     * @param loginType Specifies whether user logged in as guest or using Google+/Facebook
     * @return User User object created and returned for new user
     */
    public User signIn(String userID, String loginType) {
        return null;
    }

    public User createWithID(String id) {
        User newUser = new User();
        newUser.setUserID(id);
        
        //give user default settings- 0 wins, 0 losses, false soundSettings
        newUser.setSettings(new Settings(0, 0, false));

        users.put(id, newUser);
        return newUser;
    }
    
    @Override
    public User create() {
        //TODO - have database generate IDs
        //This workaround will do for now
        String dummyID = "id_" + String.valueOf(users.size());
        User newUser = createWithID(dummyID);
        return newUser;
    }

    @Override
    public User update(String id, String body) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * WARNING: find(id) returns NULL if User not found in database.
     * @return User returns null if User not found
     */
    @Override
    public User find(String id) {
        return users.get(id);
    }

    @Override
    public boolean delete(String id) {
        // TODO Auto-generated method stub
        return false;
    }

}
