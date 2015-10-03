/*
* Created by Sindhuula Selvaraju.
* The purpose of this class it to act as the controller that takes in the input from the frontend and returns back the outputs
*/
        package com.oose2015.sselvar4.hareandhounds;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.http.HttpClient;

import java.io.PrintWriter;
import java.nio.file.FileSystemNotFoundException;
import java.util.*;

import static spark.Spark.*;

public class HnHController {

    private static final String API_CONTEXT = "/hareandhounds/api/games";

    private final HnHService hnhService;
    private final Logger logger = LoggerFactory.getLogger(HnHController.class);

    public HnHController(HnHService hnhService) {
        this.hnhService = hnhService;
        setupEndpoints();
    }
    private void setupEndpoints() {
        /**
         * Funtion to create a new game with inputs as pieceType and output as gameId, playerId and pieceType
        */
         post(API_CONTEXT,"application/json", (request, response) -> {
            try {
                String input = request.body();
                String pieceType;
                //Check the pieceType
                if(input.contains("HOUND"))
                {
                    pieceType = "HOUND";
                }
                else
                {
                    pieceType = "HARE";
                }
                response.status(201);
               HnHPlayer newGameHnh = hnhService.createNewHnHGame(pieceType);
              return newGameHnh;
            } catch (HnHService.HnHServiceException ex) {
                logger.error("Failed to create new game");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
    /**
    * This function returns the current gameStatus taking gameId as input
    */
          get(API_CONTEXT + "/:gameId" + "/state", "application/json", (request, response) -> {
            try {
                response.status(200);
                String getState = hnhService.getHnHState(request.params(":gameId"));
                HashMap<String,String> state = new HashMap<String, String>();
                state.put("gameId",request.params(":gameId"));
                state.put("state",getState);
                return state;
            } catch (HnHService.HnHServiceException ex) {
                logger.error("Failed to get state" + request.params(":gameId"));
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        /**
         * This function returns the current board positions taking gameId as input
        */
         get(API_CONTEXT+"/:gameId"+"/board","application/json", (request, response) -> {
            try {
                response.status(200);
                HnHBoard hnhmove =  hnhService.getHnHBoard(request.params(":gameId"));
                HnHPiece hnh1 = new HnHPiece("HOUND", Integer.parseInt(hnhmove.x1), Integer.parseInt(hnhmove.y1));
                HnHPiece hnh2 = new HnHPiece("HOUND", Integer.parseInt(hnhmove.x2), Integer.parseInt(hnhmove.y2));
                HnHPiece hnh3 = new HnHPiece("HOUND", Integer.parseInt(hnhmove.x3), Integer.parseInt(hnhmove.y3));
                HnHPiece hnh4 = new HnHPiece("HARE", Integer.parseInt(hnhmove.x4), Integer.parseInt(hnhmove.y4));
                List<HnHPiece> hnhpiece = new ArrayList<HnHPiece>();
                hnhpiece.add(hnh1);
                hnhpiece.add(hnh2);
                hnhpiece.add(hnh3);
                hnhpiece.add(hnh4);
                return hnhpiece;
            } catch (HnHService.HnHServiceException ex) {
                logger.error("Failed to get board");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        /**
         * This function lets a new player join the game after checking if a second player is needed
         * by taking gameId as input.
         */
        put(API_CONTEXT + "/:gameId", "application.json", (request, response) -> {
            try {
                String checkPlayer = hnhService.getHnHState(request.params(":gameId"));
                if (checkPlayer.equals("WAITING_FOR_SECOND_PLAYER")) {
                    response.status(200);
                    return hnhService.joinHnHGame(request.params(":gameId"));
                } else {
                    System.out.print("People already playing dude");
                    response.status(410);
                }
            } catch (HnHService.HnHServiceException ex) {
                logger.error("Failed to join game");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());

        /**
         * This function lets the player move a piece if it's his turn.
         * It takes gameId, from coordinates and to coordinates as input
         * and returns if the move was successful or if it caused an error
         */
        post(API_CONTEXT + "/:gameId" + "/turns", "application/json", (request, response) -> {
            try {
                String requestContent = request.body();
                requestContent = requestContent.replace("\"","");
                requestContent = requestContent.substring(1, requestContent.length() - 1);
                /*
                Convert the request body parameters into a hashmap for easier navigation
                 */
                String[] keyValuePairs = requestContent.split(",");
                HashMap<String,String> requestMap = new HashMap<>();
                HashMap<String,String> returnThis = new HashMap<>();
                for(String pair : keyValuePairs)
                {
                    String[] entry = pair.split(":");
                    requestMap.put(entry[0].trim(), entry[1].trim());
                }
                String gameID = requestMap.get("gameId").toString();
                String playerID = requestMap.get("playerId").toString();
                returnThis.put("gameId",gameID);
                /*
                      Check if a move is expected
                 */
                if(!hnhService.getHnHState(gameID).equals("TURN_HOUND")||!hnhService.getHnHState(gameID).equals("TURN_HARE"))
                {
                    response.status(404);
                    returnThis.put("reason","INVALID_GAME_ID");
                }
                /*
                    Check if the second player has joined and return an error if not
                 */
                if(hnhService.getHnHState(gameID).equals("WAITING_FOR_SECOND_PLAYER"))
                {
                    response.status(422);
                    returnThis.put("reason","WAIT_FOR_SECOND_PLAYER");
                    return returnThis;
                }
                /*
                    Check if the game is already over and return error if it is
                 */
                if(hnhService.getHnHState(gameID).contains("WIN_"))
                {
                    response.status(422);
                    returnThis.put("reason","GAME_OVER");
                    return returnThis;
                }
                /*
                    Check if the move is valid and return error if it is not
                 */
                if (!hnhService.checkMove(requestMap))
                {

                    response.status(422);
                    returnThis.put("reason","ILLEGAL_MOVE_POSITION_OCCUPIED");
                    return returnThis;
                }
                /*
                    Check if a player of the current game is trying to make a move and return error if not
                 */
                if(!playerID.equals(gameID+"P1")||!playerID.equals(gameID+"P2"))
                {
                    response.status(404);
                    returnThis.put("reason","INVALID_PLAYER_ID");
                }

                /*
                    Check if it is that player's turn to make a move and return an error if not
                    If it really is his turn then make a move
                 */
                String checkIllegal = hnhService.checkIllegal(requestMap);
                if(checkIllegal.contains("ILLEGAL"))
                {
                    response.status(422);
                    returnThis.put("reason",checkIllegal);
                    return returnThis;
                }
                if (hnhService.checkTurn(requestMap))
                {
                    returnThis = hnhService.makeMove(requestMap);
                    if (returnThis.containsKey("reason")&&returnThis.containsValue("ILLEGAL_MOVE"))
                    {
                        response.status(422);
                    }
                    else
                    {
                        response.status(200);
                    }
                    return returnThis;
                }
                else {
                    response.status(422);
                    returnThis.put("reason","INCORRECT_TURN");
                    return returnThis;
                }

            } catch (HnHService.HnHServiceException ex) {
                logger.error("Failed to make a move");
                response.status(500);
            }
            return Collections.EMPTY_MAP;
        }, new JsonTransformer());
    }
}
