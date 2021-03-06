package com.oose2015.group13.backend;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 * Tile represents an X and Y coordinate on the game board. Tile also knows which Piece is occupying it,
 * and whether the Tile has been attacked
 */
public class Tile {
    private int xCoordinate;
    private int yCoordinate;
    private Piece occupyingPiece;
    private boolean attacked;
    private TileType tileType;
    
    
}
