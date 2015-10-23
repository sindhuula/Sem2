package com.oose2015.group13.backend.gamelogic.piece;

import java.util.HashSet;
import java.util.Set;

import com.oose2015.group13.backend.gamelogic.gameboard.TileType;

/**
 * Ship is a subclass of Piece, which overrides getAcceptedTileTypes()
 * to return SEA TileTypes only. Any subclass of Ship can only be 
 * placed on sea.
 */
public abstract class Ship extends Piece {

    @Override
    public Set<TileType> getAcceptedTileTypes() {
        return new HashSet<TileType>(){{
            add(TileType.SEA);
        }};
    }
    
}
