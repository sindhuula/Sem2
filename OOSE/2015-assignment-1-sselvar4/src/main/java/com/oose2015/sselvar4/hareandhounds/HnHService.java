/**
 * This class works to support the controller. It gets inputs from the controller and returns the output to it.
 */
package com.oose2015.sselvar4.hareandhounds;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import javax.sql.DataSource;
import java.beans.Statement;
import java.util.*;

public class HnHService {

    private Sql2o db;

    private final Logger logger = LoggerFactory.getLogger(HnHService.class);

    /**
     * Construct the model with a pre-defined datasource. The current implementation
     * also ensures that the DB schema is created if necessary.
     *
     * @param dataSource
     */
    public HnHService(DataSource dataSource) throws HnHServiceException {
        db = new Sql2o(dataSource);

        //Create the schema for the database if necessary. This allows this
        //program to mostly self-contained. But this is not always what you want;
        //sometimes you want to create the schema externally via a script.
        try (Connection conn = db.open()) {
            String sql = "CREATE TABLE IF NOT EXISTS game (gameId INTEGER PRIMARY KEY, playerId1 TEXT, playerId2 TEXT, pieceType1 TEXT, gameStatus TEXT)";
            conn.createQuery(sql).executeUpdate();
            sql = "CREATE TABLE IF NOT EXISTS moves (gameId INTEGER, moveId INTEGER, hound1X INTEGER, hound1Y INTEGER, hound2X INTEGER, hound2Y INTEGER, hound3X INTEGER, hound3Y INTEGER, hareX INTEGER, hareY INTEGER)";
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            logger.error("Failed to create schema at startup", ex);
            throw new HnHServiceException("Failed to create schema at startup", ex);
        }

    }

    /**
     *
     * @param pieceType
     * @return hnhplayer
     * @throws HnHServiceException
     * This funtion lets you create a new game by taking the pieceType as input and returns the gameId,playerId and pieceType as output
     */
    public HnHPlayer createNewHnHGame(String pieceType) throws HnHServiceException {
        String temp  = "";
        Integer tem = 0;
        try {
            Connection conn = db.open();
            String sql = "SELECT MAX(gameId) FROM game";
            temp = conn.createQuery(sql).executeScalar(String.class);
        conn.close();
        }catch (Sql2oException ex) {
            logger.error("HnHService.createNewGame: Failed to check new game", ex);
            throw new HnHServiceException("HnHService.createNewGame: Failed to check new game", ex);
        }
        if(temp == null)
        {
           tem = 1;
        }
        else
        {
            tem = Integer.parseInt(temp)+1;
        }

        String gameID = "Game" + tem;
        String playerID = gameID + "P1";
        String pieceTyp = pieceType;
        String gameStatus = "WAITING_FOR_SECOND_PLAYER";
        HnHPlayer hnhplayer = new HnHPlayer(gameID,playerID,pieceTyp);
        /**
         * Insert the data into the database
         */
        String sql = "INSERT INTO game (gameId,playerId1, pieceType1, gameStatus) " +
                "             VALUES (:gameId,:playerID, :pieceTyp, :gameStatus)";
        String sql2 = "INSERT INTO moves (gameId, moveId,hound1X, hound1Y, hound2X, hound2Y, hound3X, hound3Y, hareX, hareY)" +
                "VALUES (:gameId,'1','1','0','0','1','1','2','4','1')";
        try (Connection conn = db.open()) {
            conn.createQuery(sql)
                    .addParameter("gameId", tem)
                    .addParameter("playerID", playerID)
                    .addParameter("pieceTyp", pieceTyp)
                    .addParameter("gameStatus", gameStatus)
                    .executeUpdate();
            conn.createQuery(sql2).addParameter("gameId", tem).executeUpdate();
            conn.close();
            return hnhplayer;
        } catch (Sql2oException ex) {
            logger.error("HnHService.createNewGame: Failed to create new game", ex);
            throw new HnHServiceException("HnHService.createNewGame: Failed to create new game", ex);
        }
    }

    /**
     *
     * @param gameID
     * @return state
     * @throws HnHServiceException
     * This function takes the gameId as input and returns the current status of the game
     */
    public String getHnHState(String gameID) throws HnHServiceException {
        String ID = gameID.replaceAll("\\D+","");
        String sql = "SELECT gameStatus from game WHERE gameId = " + ID;
        try (Connection conn = db.open()) {
            String gameStatus = conn.createQuery(sql)
                        .addColumnMapping("gameStatus", "state")
                        .executeScalar(String.class);
          return gameStatus;
        } catch(Sql2oException ex) {
            logger.error(String.format("HnHService.find: Failed to query database for id: %s to get state", gameID), ex);
            throw new HnHServiceException(String.format("HnHService.find: Failed to query database for id: %s to get state", gameID), ex);
        }
    }

    /**
     *
     * @param gameID
     * @return hnhboard
     * @throws HnHServiceException
     * This function returns the current position of the pieces on the board for the gameId which is given as input
     */
    public HnHBoard getHnHBoard(String gameID) throws HnHServiceException {
        String ID = gameID.replaceAll("\\D+", "");
        String sql = "SELECT * from moves WHERE gameId = :gameID ORDER BY moveId DESC";
        try (Connection conn = db.open()) {

          HnHBoard hnhboard =  conn.createQuery(sql)
                    .addParameter("gameID", ID)
                    .addColumnMapping("gameId", "game_id")
                    .addColumnMapping("moveId", "move_no")
                    .addColumnMapping("hound1X", "x1")
                    .addColumnMapping("hound1Y", "y1")
                    .addColumnMapping("hound2X", "x2")
                    .addColumnMapping("hound2Y", "y2")
                    .addColumnMapping("hound3X", "x3")
                    .addColumnMapping("hound3Y", "y3")
                    .addColumnMapping("hareX", "x4")
                    .addColumnMapping("hareY", "y4")
                    .executeAndFetchFirst(HnHBoard.class);
            return hnhboard;
        }
      catch(Sql2oException ex) {
            logger.error(String.format("HnHService.find: Failed to query database for id: %s to get board", gameID), ex);
            throw new HnHServiceException(String.format("HnHService.find: Failed to query database for id: %s to get board"+ gameID), ex);
        }
    }

    /*
        This function lets a second player join the game(by taking gameId as input) is there's still a vacancy and returns the new player's Id
     */
    public HnHPlayer joinHnHGame(String gameId) throws HnHServiceException {
        String playerID = gameId + "P2";
        String ID = gameId.replaceAll("\\D+","");
        String gameStatus = "TURN_HOUND";
        String sql = "SELECT pieceType1 FROM game WHERE gameId = :gameID";

        try (Connection conn1 = db.open()) {
         String pieceType2 =conn1.createQuery(sql)
                    .addParameter("gameID", ID)
                    .addColumnMapping("pieceType1","pieceType2")
                    .executeScalar(String.class);
                String sql2 = "UPDATE game SET playerId2 = :play2, gameStatus = :gamestat WHERE gameId = :gameID";
                conn1.createQuery(sql2)
                        .addParameter("gameID",ID)
                        .addParameter("play2", playerID)
                        .addParameter("gamestat",gameStatus)
                        .executeUpdate();
                if (pieceType2.equals("HOUND"))
                {
                    pieceType2 = "HARE";
                }
                else
                {
                    pieceType2 = "HOUND";
                }
                HnHPlayer hnh = new HnHPlayer(gameId,playerID,pieceType2);
                return hnh;
        } catch (Sql2oException ex) {
            logger.error("HnHService.joinGame: Failed to join game", ex);
            throw new HnHServiceException("HnHService.findGame: Failed to join game", ex);
        }
    }

    /**
     *
     * @param requestMap
     * @return true or false
     * @throws HnHServiceException
     * This function checks if the correct player is trying to move a piece on the board for the correct game
     * It returns true or false based on whether it's their turn or not
     */
    public boolean checkTurn(HashMap<String,String> requestMap) throws  HnHServiceException
    {
        String gameID = requestMap.get("gameId").toString();
        String playerID = requestMap.get("playerId").toString();
        String ID = gameID.replaceAll("\\D+", "");
        String sql = "SELECT pieceType1 FROM game WHERE gameId = :gameID";
        try(Connection conn = db.open())
        {
            String pieceType1 = conn.createQuery(sql)
                    .addParameter("gameID", ID)
                    .executeScalar(String.class);
            String checking = getHnHState(gameID);
            if (playerID.contains("P1"))
            {
                if(pieceType1.equals("HOUND") && checking.equals("TURN_HOUND"))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if(pieceType1.equals("HOUND") && checking.equals("TURN_HOUND"))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        catch (Sql2oException ex) {
            logger.error("HnHService.checkTurn: Failed to get turn", ex);
            throw new HnHServiceException("HnHService.checkTurn: Failed to get turn", ex);
        }
    }

    /**
     *
     * @param gameId
     * @return state
     * @throws HnHServiceException
     * This function checks if the player has won the game when he makes a move
     * It takes gameId,from and to coordinates as input and returns if the winning condition has been reached
     */
    public String checkWin(String gameId) throws HnHServiceException {
        HnHBoard checkMove = getHnHBoard(gameId);
        String state = getHnHState(gameId);
        String ID = gameId.replaceAll("\\D+", "");
        String board = checkMove.x1+checkMove.y1+";"+checkMove.x2+checkMove.y2+";"+checkMove.x3+checkMove.y3;
        String hare = checkMove.x4+checkMove.y4;
        /**
         * Check if the hound's winnning condition has been reached
         */
        if((board.contains("10")&&(board.contains("21"))&&(board.contains("30"))&&hare.contains("20")))
        {
            return "WIN_HOUND";
        }
        else if (board.contains("12")&&(board.contains("21"))&&(board.contains("32"))&&hare.contains("22"))
        {
            return "WIN_HOUND";
        }
        else if (board.contains("30")&&(board.contains("31"))&&(board.contains("32"))&&hare.contains("41"))
        {
            return "WIN_HOUND";
        }
        /*
            check if the hare has escaped
         */
        else if ((Integer.parseInt(checkMove.x4) <= Integer.parseInt(checkMove.x1)) &&
                (Integer.parseInt(checkMove.x4) <= Integer.parseInt(checkMove.x2)) &&
                (Integer.parseInt(checkMove.x4) <= Integer.parseInt(checkMove.x3))) {
            return "WIN_HARE_BY_ESCAPE";
        }
        /**
         * Check for a stalemate.
         */
        /*
            Check if the hounds are trying to stall
         */
        else
        {
            String sql = "SELECT * FROM moves WHERE gameId = :gameID" ;
            try (Connection conn = db.open()) {
                List<HnHBoard> hnhboards = conn.createQuery(sql)
                        .addParameter("gameID", ID)
                        .addColumnMapping("gameId", "game_id")
                        .addColumnMapping("moveId", "move_no")
                        .addColumnMapping("hound1X", "x1")
                        .addColumnMapping("hound1Y", "y1")
                        .addColumnMapping("hound2X", "x2")
                        .addColumnMapping("hound2Y", "y2")
                        .addColumnMapping("hound3X", "x3")
                        .addColumnMapping("hound3Y", "y3")
                        .addColumnMapping("hareX", "x4")
                        .addColumnMapping("hareY", "y4")
                        .executeAndFetch(HnHBoard.class);
                int[] count = new int[hnhboards.size()];
                for (int i = 0; i < hnhboards.size() - 1; i++) {
                    String[] istr = new String[4];

                    count[i] = 0;
                    istr = hnhboards.get(i).convert();
                    for (int j = 1; j < hnhboards.size(); j++) {
                        String[] jstr = hnhboards.get(j).convert();
                        Arrays.sort(istr);
                        Arrays.sort(jstr);

                        if (Arrays.equals(istr, jstr)) {
                            count[i]++;
                        }
                    }
                        if (count[i] > 3) {
                            return "WIN_HARE_BY_STALLING";
                        }
                }
            return state;
                } catch (Sql2oException ex) {
                    logger.error("HnHService.checkWin: Failed to check for win", ex);
                    throw new HnHServiceException("HnHService.checkWin: Failed to check for win", ex);
                }
            }
    }

    /**
     *
     * @param requestMap
     * @return true or false
     * @throws HnHServiceException
     * This function checks if the player is trying to move to a position that's already occupied
     */
    public boolean checkMove(HashMap<String,String> requestMap) throws HnHServiceException {
        String gameID = requestMap.get("gameId").toString();
        String playerID = requestMap.get("playerId").toString();
        String from_x = requestMap.get("fromX").toString();
        String from_y = requestMap.get("fromY").toString();
        String to_x = requestMap.get("toX").toString();
        String to_y = requestMap.get("toY").toString();
        String ID = gameID.replaceAll("\\D+", "");
        String sql = "SELECT * FROM moves WHERE gameId = :gameID ORDER BY moveId DESC";
        try (Connection conn = db.open()) {
            HnHBoard hnhboards = conn.createQuery(sql)
                    .addParameter("gameID", ID)
                    .addColumnMapping("gameId", "game_id")
                    .addColumnMapping("moveId", "move_no")
                    .addColumnMapping("hound1X", "x1")
                    .addColumnMapping("hound1Y", "y1")
                    .addColumnMapping("hound2X", "x2")
                    .addColumnMapping("hound2Y", "y2")
                    .addColumnMapping("hound3X", "x3")
                    .addColumnMapping("hound3Y", "y3")
                    .addColumnMapping("hareX", "x4")
                    .addColumnMapping("hareY", "y4")
                    .executeAndFetchFirst(HnHBoard.class);
            if (!(from_x.equals(to_x) && from_y.equals(to_y)) && ((to_x.equals(hnhboards.x1) && to_y.equals(hnhboards.y1)) || (to_x.equals(hnhboards.x2) && to_y.equals(hnhboards.y2)) ||
                    (to_x.equals(hnhboards.x3) && to_y.equals(hnhboards.y3)) || (to_x.equals(hnhboards.x4) && to_y.equals(hnhboards.y4)))) {
                     return false;
            }
            else {
                return true;
            }
        }catch (Sql2oException ex) {
            logger.error("HnHService.checkWin: Failed to check the move", ex);
            throw new HnHServiceException("HnHService.checkWin: Failed to check the move", ex);
        }

    }

    /**
     *
     * @param requestMap
     * @return retuenThis
     * @throws HnHServiceException
     * This function checks if a player is trying to make an illegal move like moving more than 1 block or
     * moving to a spot with no path from the current spot
     */
    public String checkIllegal(HashMap<String,String> requestMap) throws HnHServiceException
    {
        Integer from_x = Integer.parseInt(requestMap.get("fromX").toString());
        Integer from_y = Integer.parseInt(requestMap.get("fromY").toString());
        Integer to_x = Integer.parseInt(requestMap.get("toX").toString());
        Integer to_y = Integer.parseInt(requestMap.get("toY").toString());
        int flag = 0;
        String returnThis="SAFE";
        if((Math.abs(from_x-to_x)>1)||(Math.abs(from_y-to_y)>1))
        {
            returnThis = "ILLEGAL_MOVE_MOVE_YOU_CAN_MOVE_ONLY_TO_ADJACENT_SPOT";
            return returnThis;
        }
        if(((from_x == 1)&&(from_y==1)))
        {
            if(((to_x == 2)&&(to_y==0)))
            {
              flag =1;
            }
            else if (((to_x == 2)&&(to_y==2)))
            {
                flag = 1;
            }
        }
        else if(((from_x == 3)&&(from_y==1)))
        {
            if(((to_x == 2)&&(to_y==0)))
            {
                flag =1;
            }
            else if (((to_x == 2)&&(to_y==2)))
            {
                flag = 1;
            }
        }
        else if(((from_x == 2)&&(from_y==0)))
        {
            if(((to_x == 1)&&(to_y==1)))
            {
                flag =1;
            }
            else if (((to_x == 3)&&(to_y==1)))
            {
                flag = 1;
            }
        }
        else if(((from_x == 2)&&(from_y==2)))
        {
            if(((to_x == 1)&&(to_y==1)))
            {
                flag =1;
            }
            else if (((to_x == 3)&&(to_y==1)))
            {
                flag = 1;
            }
        }
        if(flag == 1)
        {
            returnThis = "ILLEGAL_MOVE_NO_PATH";
        }
        return returnThis;
    }
    /**
     *
     * @param requestMap
     * @return hashMap of results
     * @throws HnHServiceException
     * This functions moves a piece from it's current to it's new position if it's a valid move
     */
    public HashMap<String,String> makeMove(HashMap<String,String> requestMap) throws HnHServiceException
    {
        String gameID = requestMap.get("gameId").toString();
        String playerID = requestMap.get("playerId").toString();
        String from_x = requestMap.get("fromX").toString();
        String from_y = requestMap.get("fromY").toString();
        String to_x = requestMap.get("toX").toString();
        String to_y = requestMap.get("toY").toString();
        String ID = gameID.replaceAll("\\D+", "");
        HnHBoard hnhboard = getHnHBoard(gameID);
        Integer moveId = Integer.parseInt(hnhboard.move_no.toString());
        HnHPiece hnh1 = new HnHPiece("HOUND", Integer.parseInt(hnhboard.x1), Integer.parseInt(hnhboard.y1));
        HnHPiece hnh2 = new HnHPiece("HOUND", Integer.parseInt(hnhboard.x2), Integer.parseInt(hnhboard.y2));
        HnHPiece hnh3 = new HnHPiece("HOUND", Integer.parseInt(hnhboard.x3), Integer.parseInt(hnhboard.y3));
        HnHPiece hnh4 = new HnHPiece("HARE", Integer.parseInt(hnhboard.x4), Integer.parseInt(hnhboard.y4));
        List<HnHPiece> hnhpiece = new ArrayList<>();
        hnhpiece.add(hnh1);
        hnhpiece.add(hnh2);
        hnhpiece.add(hnh3);
        hnhpiece.add(hnh4);
        HnHPiece check1 = new HnHPiece("HOUND",Integer.parseInt(from_x),Integer.parseInt(from_y));
        HashMap<String,String> returnThis = new HashMap<>();
        moveId+=1;

        if (hnhpiece.contains(check1)) {
                if (Integer.parseInt(to_x) < Integer.parseInt(from_x)) {
                    returnThis.put("reason","ILLEGAL_MOVE");
                    return returnThis;
                }
                    Integer pos = hnhpiece.indexOf(check1) + 1;
                    switch (pos) {
                        case 1:
                            hnh1 = new HnHPiece("HOUND", Integer.parseInt(to_x), Integer.parseInt(to_y));
                            break;
                        case 2:
                            hnh2 = new HnHPiece("HOUND", Integer.parseInt(to_x), Integer.parseInt(to_y));
                            break;
                        case 3:
                            hnh3 = new HnHPiece("HOUND", Integer.parseInt(to_x), Integer.parseInt(to_y));
                            break;
                    }
            } else {
              hnh4 = new HnHPiece("HOUND", Integer.parseInt(to_x), Integer.parseInt(to_y));
            }
            String sql = "INSERT INTO moves (gameId, moveId,hound1X, hound1Y, hound2X, hound2Y, hound3X, hound3Y, hareX, hareY)" +
                    "VALUES (:gameId,:moveId,:x1,:y1,:x2,:y2,:x3,:y3,:x4,:y4)";
            try (Connection conn = db.open()) {
                conn.createQuery(sql)
                        .addParameter("gameId", ID)
                        .addParameter("moveId", moveId)
                        .addParameter("x1", hnh1.getx())
                        .addParameter("y1", hnh1.gety())
                        .addParameter("x2", hnh2.getx())
                        .addParameter("y2", hnh2.gety())
                        .addParameter("x3", hnh3.getx())
                        .addParameter("y3", hnh3.gety())
                        .addParameter("x4", hnh4.getx())
                        .addParameter("y4", hnh4.gety())
                        .executeUpdate();
                String state = checkWin(gameID);
                if (state.equals("TURN_HOUND")){
                    state = "TURN_HARE";
                }
                else if(state.equals("TURN_HARE"))  {
                    state = "TURN_HOUND";
                }
                sql = "UPDATE game SET gameStatus = :state where gameId = :gameId";
                conn.createQuery(sql)
                        .addParameter("gameId", ID)
                        .addParameter("state", state)
                        .executeUpdate();
                returnThis.put("playerId", playerID);
                return returnThis;
            } catch (Sql2oException ex) {
                logger.error("HnHService.makeMove: Failed to make a move", ex);
                throw new HnHServiceException("HnHService.makeMove: Failed to make a move", ex);
            }
    }

    //-----------------------------------------------------------------------------//
    // Helper Classes and Methods
    //-----------------------------------------------------------------------------//

    public static class HnHServiceException extends Exception {
        public HnHServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * This Sqlite specific method returns the number of rows changed by the most recent
     * INSERT, UPDATE, DELETE operation. Note that you MUST use the same connection to get
     * this information
     */

    private int getChangedRows(Connection conn) throws Sql2oException {
        return conn.createQuery("SELECT changes()").executeScalar(Integer.class);
    }
}
