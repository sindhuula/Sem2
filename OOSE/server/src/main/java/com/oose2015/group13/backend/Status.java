package com.oose2015.group13.backend;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 * Status enum describes status of the Game (whether we are waiting for players, or the game is over, etc...)
 */
public enum Status {
    WAITING_FOR_PLAYERS, SHIPS_NOT_PLACED, PLAYER_TURN, GAME_OVER, GAME_INCOMPLETE;
}
