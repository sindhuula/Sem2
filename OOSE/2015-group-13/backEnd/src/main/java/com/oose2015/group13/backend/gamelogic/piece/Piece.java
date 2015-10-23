package com.oose2015.group13.backend.gamelogic.piece;

import java.util.Set;

import com.oose2015.group13.backend.gamelogic.gameboard.TileType;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * Piece is an abstract class that describes a game piece. Subclasses are 
 * Ship, LandPiece, AmphibiousPiece.
 */
public abstract class Piece {
    private String pieceName;
    
    //Number of tiles for piece that have not been hit..
    //At the beginning of the game, numberTilesRemaining is the total number of tiles
    //for this piece
    protected int numberTilesRemaining;
    protected int piecePoints;
    
    /*
     * subtracts 1 from numberTilesRemaining on Piece
     * @return success
     */
    public boolean hitPiece() {
        if(numberTilesRemaining > 0) {
            numberTilesRemaining--;
            return true;
        } else {
            return false;
        }
        
    }
    
    /*
     * @return whether piece has been sunk (all pieces in piece have been hit)
     */
    public boolean isSunk() {
        return (numberTilesRemaining == 0);
    }
    
    /**
     * Get the TileTypes that this Piece can be placed on.
     * @return Set of TileType enums
     */
    public abstract Set<TileType> getAcceptedTileTypes();

    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public int getPiecePoints() {
        return piecePoints;
    }

    public void setPiecePoints(int piecePoints) {
        this.piecePoints = piecePoints;
    }

    public int getNumberTilesRemaining() {
        return numberTilesRemaining;
    }
}
