package com.oose2015.group13.backend.gamelogic.gameboard;

/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * Game Board object. Contains a multidimensional array of Tile objects.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {
    //Logger object
    private final Logger logger = LoggerFactory.getLogger(Board.class);

    //Name of this board layout
    private String boardLayout;
    private Tile[][] tiles;
    
    /**
     * @param x coordinate of the tile to attack
     * @param y coordinate of the tile to attack
     * @return Result of the move
     */
    public Result attackTile(int x, int y) {
        return null;
    }
    
    /**
     * @param tile to attack
     * @result Result object for whether desired Tile was hit
     */
    private Result checkTile(int x, int y) throws Tile.TileNotFoundException {
        try {
            Tile tileFromTileArray = tiles[x][y];
            if (tileFromTileArray.isOccupied()) {
                return Result.HIT;
            } else {
                return Result.MISS;
            }
        }
        catch(ArrayIndexOutOfBoundsException arrayIndexException) {
            logger.error("Tile coordinates exceed board limit.");
            throw new Tile.TileNotFoundException("Tile coordinates exceed board limit",
                    arrayIndexException);
        }   
    }



}
