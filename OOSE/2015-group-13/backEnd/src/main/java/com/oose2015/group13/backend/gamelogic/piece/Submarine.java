package com.oose2015.group13.backend.gamelogic.piece;

/**
 * Submarine is a subclass of Ship, which is a subclass of Piece
 * Submarine can only be placed on SEA TileTypes, and contains 2 tiles.
 */
public class Submarine extends Ship {
    public Submarine() {
        numberTilesRemaining = 2;
    }
}
