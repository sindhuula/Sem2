package com.oose2015.group13.backend;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * UserService acts as an interface between UserController and functions for user
 */
public class UserService {
    String DataSoure;

    /**
     * This is the constructor for the GameService and initializes/opens the database
     * @param DataSource
     */
    public UserService(String DataSource)
    {

    }

    /**
     * This function signs in the user and retrieves their data from the database
     * @param userID
     * @param loginType
     * @return
     */
    public User signIn(String userID, String loginType)
    {
        return null;
    }

    /**
     * This function creates a new user's data if it already does not exists in the database
     * and if the user did not login as guest
     * @param newUser
     */
    private void createUser(User newUser)
    {

    }

    /**
     * This function enables the user to modify data like settings etc
     * @param modify
     */
    public void modifyUser(User modify)
    {

    }

    /**
     * This function enables the ser record to be deleted from the database
     * @param delete
     */
    public void deleteUser(User delete)
    {

    }
}
