package com.oose2015.group13.backend.gamelogic.piece;

/**
 * AmphibiousVehicle is a subclass of AmphibiousPiece, which is a subcass of Piece.
 * AmphibiousVehicle can be placed on land or sea, and has 2 tiles
 *
 */
public class AmphibiousVehicle extends AmphibiousPiece {
    public AmphibiousVehicle() {
        numberTilesRemaining = 2;
    }
}
