//Code based on todo app test case, and Ben Leibowitz Assignment 1 unit test
package com.oose2015.group13.backend.gamelogic.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

import com.google.gson.Gson;
import com.oose2015.group13.backend.server.Server;

/**
 * 
 * using code from Todo app test case
 * @see Todo app
 */
public class TestNewGame {

    private static final String CTX = "/battleship/api";

    @Before
    public void setUp() throws Exception {
        Server.main(null);
        Spark.awaitInitialization();
    }

    @After
    public void tearDown() throws Exception {
        Spark.stop();
    }

    @Test
    public void testNewGame() {
        //Start games with human players
        Response resp = request("POST", CTX + "/games/new",
                "{^userID^:^myUserID^, ^challengedUserIDs^:[^challengedUserID1^, ^challengedUserID2^], ^gameSettings^:{^boardType^:^myBoardType^, ^boardSize^:10}}".replace('^', '"'));
        JSONObject json = new JSONObject(resp.content);
        assertEquals("0", json.getString("gameID"));
        
        Response resp2 = request("POST", CTX + "/games/new",
                "{^userID^:^frankJoe^, ^challengedUserIDs^:[^challengedUserID1^, ^challengedUserID2^], ^gameSettings^:{^boardType^:^myBoardType^, ^boardSize^:10}}".replace('^', '"'));
        JSONObject json2 = new JSONObject(resp2.content);
        assertEquals("1", json2.getString("gameID"));
        
        //Start a game with AI
        Response resp3 = request("POST", CTX + "/games/new",
                "{^userID^:^djTrump^, ^challengedUserIDs^:[], ^gameSettings^:{^boardType^:^myBoardType^, ^boardSize^:10}}".replace('^', '"'));
        JSONObject json3 = new JSONObject(resp3.content);
        assertEquals("2", json3.getString("gameID"));

    }

    //Using request method from todo app test case
    private Response request(String method, String path, String str) {
        HttpURLConnection http = null;
        try {
            URL url = new URL("http", "localhost", 8080, path);
            //System.out.println(url);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoInput(true);
            if (str != null) {
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter output = new OutputStreamWriter(http.getOutputStream());
                output.write(str);
                output.flush();
                output.close();
            }

            String responseBody = IOUtils.toString(http.getInputStream());
            return new Response(http.getResponseCode(), responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    //Using request method based on todo app test case
    private String requestWithError(String method, String path, String str) {
        HttpURLConnection http = null;
        try {
            URL url = new URL("http", "localhost", 8080, path);
            //System.out.println(url);
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoInput(true);
            if (str != null) {
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                OutputStreamWriter output = new OutputStreamWriter(http.getOutputStream());
                output.write(str);
                output.flush();
                output.close();
            }

            String errCode = String.valueOf(http.getResponseCode());
            return errCode;
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }


    //Using Response class from todo app test case
    private static class Response {

        public String content;

        public int httpStatus;

        public Response(int httpStatus, String content) {
            this.content = content;
            this.httpStatus = httpStatus;
        }

        public <T> T getContentAsObject(Type type) {
            return new Gson().fromJson(content, type);
        }
    }

}
