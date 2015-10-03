/**
 * Created by Sindhuula Selvaraju
 * This class create objects that store the pieceType and coordinates for a player
 */
package com.oose2015.sselvar4.hareandhounds;

public class HnHPiece {

    private String pieceType;
    private Integer x;
    private Integer y;

    public HnHPiece(String pieceType, Integer x, Integer y) {
        this.pieceType = pieceType;
        this.x = x;
        this.y = y;
    }

    public String getPieceType() {return pieceType;
    }
    public Integer getx() {return x;  }
    public Integer gety() {return y;
    }
    @Override
    /**
     * Function to check if 2 objects of the class are equal
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HnHPiece hnhpiece = (HnHPiece) o;

        if (pieceType != hnhpiece.pieceType) return false;
        if (x != null ? !x.equals(hnhpiece.x) : hnhpiece.x != null) return false;
        if (y != null ? !y.equals(hnhpiece.y) : hnhpiece.y != null) return false;
        return true;
    }
}
