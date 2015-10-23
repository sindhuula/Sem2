package com.oose2015.group13.backend.gamelogic.piece;

import java.util.HashSet;
import java.util.Set;

import com.oose2015.group13.backend.gamelogic.gameboard.TileType;

/**
 * AmphibiousPiece is a Piece object that can be placed on land or 
 * water. It overrides getAcceptedTileTypes() to return LAND and SEA 
 * TileType enums
 */
public abstract class AmphibiousPiece extends Piece {
    @Override
    public Set<TileType> getAcceptedTileTypes() {
        return new HashSet<TileType>(){{
            add(TileType.LAND);
            add(TileType.SEA);
        }};
    }
}
