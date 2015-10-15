package com.oose2015.group13.backend;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 * Game Board object. Contains a multidimensional array of Tile objects.
 */
public class Board {
    //Name of this board layout
    private String boardLayout;
    private Tile[][] tiles;
    
    /**
     * @param x, y are the coordinates of the tile to attack
     * @return Result of the move
     */
    public Result attackTile(int x, int y) {
    }
    
    /**
     * @param Tile to attack
     * @result Result object for whether desired Tile was hit
     */
    private Result checkTile(Tile tile) {
    }
}
