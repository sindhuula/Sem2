package com.oose2015.group13.backend.gamelogic.piece;

/**
 * Tank is a subclass of LandPiece, meaning it can only be placed on land.
 * Tank contains 1 tile.
 */
public class Tank extends LandPiece {
    public Tank() {
        numberTilesRemaining = 1;
    }
}
