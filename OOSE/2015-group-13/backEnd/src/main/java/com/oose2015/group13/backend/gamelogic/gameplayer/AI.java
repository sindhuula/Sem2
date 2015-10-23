package com.oose2015.group13.backend.gamelogic.gameplayer;
import java.util.Map;

import com.oose2015.group13.backend.user.User;
/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * AI class, a subclass of Player. AI can be tuned for different levels.
 */
public class AI extends Player {
    public AI(User parentUser) {
        
    }

    private String algorithmName;
    
    /*
     * Called when we want to get the AI to move.
     * @return Map<String, String> of ie... {"x":"3", "y":"5", "userID":"fooBar"}
     */
    private Map<String, String> makeMove() {
    return null;
    }
}
