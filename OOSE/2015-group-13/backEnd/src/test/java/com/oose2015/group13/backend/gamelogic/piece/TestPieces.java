package com.oose2015.group13.backend.gamelogic.piece;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oose2015.group13.backend.gamelogic.gameboard.TileType;
import com.oose2015.group13.backend.gamelogic.piece.AmphibiousVehicle;
import com.oose2015.group13.backend.gamelogic.piece.Battleship;
import com.oose2015.group13.backend.gamelogic.piece.Destroyer;
import com.oose2015.group13.backend.gamelogic.piece.Piece;
import com.oose2015.group13.backend.gamelogic.piece.Submarine;
import com.oose2015.group13.backend.gamelogic.piece.Tank;

public class TestPieces {
    Piece dest;
    Piece batt;
    Piece sub;
    Piece tank;
    Piece amph;
    
    @Before
    public void setUp() throws Exception {
        dest = new Destroyer();
        batt = new Battleship();
        sub = new Submarine();
        tank = new Tank();
        amph = new AmphibiousVehicle();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAcceptedTileTypes() {
        assertEquals(new HashSet<TileType>(){{add(TileType.SEA);}}, dest.getAcceptedTileTypes());
        assertEquals(new HashSet<TileType>(){{add(TileType.SEA);}}, batt.getAcceptedTileTypes());
        assertEquals(new HashSet<TileType>(){{add(TileType.SEA);}}, sub.getAcceptedTileTypes());
        assertEquals(new HashSet<TileType>(){{add(TileType.LAND);}}, tank.getAcceptedTileTypes());
        assertEquals(new HashSet<TileType>(){{add(TileType.LAND);add(TileType.SEA);}}, amph.getAcceptedTileTypes());
    }
    
    @Test
    public void testNumberTiles() {
        //Map of pieces, to how many tiles they should have left
        Map<Piece, Integer> tiles = new HashMap<Piece, Integer>(){{
            put(dest, 3);
            put(batt, 4);
            put(tank, 1);
            put(sub, 2);
            put(amph, 2);
        }};
        
        //We need to loop until all pieces have 0 tiles left
        boolean canExit = false;
        do {
            canExit = true;
            for(int i : tiles.values()) {
                if(i != 0)
                    canExit = false;
            }
            
            //If there are > 0 tiles, first make sure piece.isSunk() is false.
            //Then make sure hitting the piece returns TRUE success,
            //and gives us 1 less tile for that piece.
            //If there are 0 tiles left, make sure piece.isSunk() == true, and make sure
            // piece.hitPiece() returns false (because we can't hit this piece successfully)
            for(Piece piece : tiles.keySet()) {
                assertEquals((int)tiles.get(piece), piece.getNumberTilesRemaining());
                if(tiles.get(piece) > 0) {
                    assertFalse(piece.isSunk());
                    assertTrue(piece.hitPiece());
                    assertEquals((int)tiles.get(piece) - 1, piece.getNumberTilesRemaining());
                    tiles.put(piece, tiles.get(piece) - 1);
                } else {
                    assertTrue(piece.isSunk());
                    assertFalse(piece.hitPiece());
                }
            }
        } while(!canExit);
    }

}
