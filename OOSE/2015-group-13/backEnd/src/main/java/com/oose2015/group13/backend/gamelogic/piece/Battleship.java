package com.oose2015.group13.backend.gamelogic.piece;

/**
 * Battleship is a subclass of Ship, which is a subclass of Piece.
 * Battleship can be placed on only SEA TileTypes,
 * and has 4 tiles.
 */
public class Battleship extends Ship {
    public Battleship() {
        numberTilesRemaining = 4;
    }
}
