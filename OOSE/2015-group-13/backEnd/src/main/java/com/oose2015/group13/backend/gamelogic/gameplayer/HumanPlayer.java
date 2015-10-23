package com.oose2015.group13.backend.gamelogic.gameplayer;

import com.oose2015.group13.backend.user.User;

public class HumanPlayer extends Player {
    public HumanPlayer(User parent) {
        this.parent = parent;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
    }
    
}
