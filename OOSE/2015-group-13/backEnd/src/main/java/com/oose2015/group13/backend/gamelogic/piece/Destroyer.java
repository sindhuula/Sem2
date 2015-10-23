package com.oose2015.group13.backend.gamelogic.piece;
/**
 * Destroyer is a subclass of Ship, which is a subclass of Piece.
 * Destroyer can be placed on only SEA TileTypes,
 * and has 3 tiles.
 */
public class Destroyer extends Ship {
    public Destroyer() {
        numberTilesRemaining = 3;
    }
}
