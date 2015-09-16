/**
 * Created by Sindhuula Selvaraju
 * This class saves the details of the player like gameId, playerId and pieceType
 */
package com.oose2015.sselvar4.hareandhounds;

import java.lang.Integer;
import java.util.Date;
import java.util.HashMap;
import java.util.StringJoiner;

public class HnHPlayer {
    private String gameId;
    private String  playerId;
    private String pieceType;
    public HnHPlayer(String gameId, String playerId, String pieceType)
    {
        this.gameId = gameId;
        this.playerId = playerId;
        this.pieceType =pieceType;
    }
 /*
     Following functions not used since version3 of the assignment.
     Might come in handy later
  */
    public String getGameId() {
        return gameId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPieceType()
    {
        return pieceType;
    }

    /**
     * @return HashMap of the object members
     */
    public HashMap<String,String> hashIt()
    {
        HashMap hash = new HashMap();
        hash.put("gameId",this.gameId);
        hash.put("playerId",this.playerId);
        hash.put("pieceType",this.pieceType);
        return hash;
    }
    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + (playerId != null ? playerId.hashCode() : 0);
        result = 31 * result + (pieceType != null ? pieceType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HnHPlayer{" +
                "gameId='" + gameId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", pieceType=" + pieceType +
               '}';
    }
}