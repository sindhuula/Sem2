package com.oose2015.group13.backend;
import java.util.Set;
/**
 * Created by OOSE Group 13 on 10/11/2015.
 */
/**
 * PieceType describes a Piece. Certain PieceTypes can only go on certain TileTypes...
 * ie... SHIP can only go on TileType.SEA
 */
public enum PieceType {
    SHIP, TANK, AMPHIBIOUS;
    
    /**
     * @return Set of accepted TileType objects, that this PieceType can land on
     */
    public Set<TileType> getAcceptedTileTypes() {
        return null;
    }
}
