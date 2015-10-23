package com.oose2015.group13.backend.gamelogic.gameboard;

import com.oose2015.group13.backend.gamelogic.piece.Piece;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * Tile represents an X and Y coordinate on the game board. Tile also knows which Piece is occupying it,
 * and whether the Tile has been attacked
 */
public class Tile {
    private int xCoordinate;
    private int yCoordinate;
    private Piece occupyingPiece;
    private boolean attacked;
    private TileType tileType;

    /**
     * isOccupied returns whether a tile is occupied or not
     * @return Boolean Returns True if tile is occupied, false if not occupied
     */
    public Boolean isOccupied()
    {
        return true;
    }

    /**
     * This function puts the value for occupying piece
     * @param p Piece object
     */
    public void setOccupyingPiece(Piece p)
    {

    }

    public static class TileNotFoundException extends Exception {
        public TileNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
