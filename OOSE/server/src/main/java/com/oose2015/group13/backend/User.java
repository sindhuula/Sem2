package com.oose2015.group13.backend;
import java.util.List;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 * User class temporarily saves information about the current user
 */
public class User {
    String userID;
    LoginType LoginType;
    Settings settings;
    List<Player> playingGames;
    Integer credits;

    /**
     * This function returns the total number of credits earned by the user
     * @param credits
     */
    public void addCredits(Integer credits)
    {
        
    }

}
