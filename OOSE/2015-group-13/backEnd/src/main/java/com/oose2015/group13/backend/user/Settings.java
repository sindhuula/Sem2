package com.oose2015.group13.backend.user;
/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * Settings object, which each User has. Describes certain "default" settings,
 * and wins and losses pertaining to this User.
 */
public class Settings {
    private String defaultBackgroundImage;
    private boolean soundSettings;
    private int wins;
    private int losses;
    
    public Settings() {
    }
    
    public Settings(int wins, int losses, boolean soundSettings) {
        this.wins = wins;
        this.losses = losses;
        this.soundSettings = soundSettings;
    }
    
    public void addWin() {
    }
    
    public void addLoss() {
    }
}
