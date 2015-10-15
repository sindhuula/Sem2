package com.oose2015.group13.backend;

import java.util.Set

/**
 * Created by OOSE Group 13 on 10/11/2015.
 * Piece describes a game piece such as Carrier, Destroyer, Aircraft Carrier, etc.
 * Piece has a PieceType enum, 
 */
public class Piece {
    private PieceType pieceType;
    private String pieceName;
    
    //Number of tiles for piece that have not been hit..
    //At the beginning of the game, numberTilesRemaining is the total number of tiles
    //for this piece
    private int numberTilesRemaining;
    private int piecePoints;
    
    /*
     * subtracts 1 from numberTilesRemaining on Piece
     */
    public void hitPiece() {
    }
    
    /*
     * @return whether piece has been sunk (all pieces in piece have been hit)
     */
    public boolean isSunk() {
    }
}
