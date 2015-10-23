package com.oose2015.group13.backend.gamelogic.piece;

import java.util.HashSet;
import java.util.Set;

import com.oose2015.group13.backend.gamelogic.gameboard.TileType;

/**
 * LandPiece is a subclass of Piece, which overrides getAcceptedTileTypes()
 * to return LAND TileTypes only. Any subclass of LandPiece can only be 
 * placed on land.
 */
public abstract class LandPiece extends Piece {

    @Override
    public Set<TileType> getAcceptedTileTypes() {
        return new HashSet<TileType>(){{
            add(TileType.LAND);
        }};
    }
    
}
