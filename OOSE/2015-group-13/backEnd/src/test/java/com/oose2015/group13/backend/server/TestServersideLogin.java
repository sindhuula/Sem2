//Code based on todo app test case, and Ben Leibowitz Assignment 1 unit test
package com.oose2015.group13.backend.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;
import spark.utils.IOUtils;

import com.google.gson.Gson;

/**
 * 
 * @author Ben Leibowitz, using code from Todo app test case
 * @see Todo app
 */
public class TestServersideLogin {

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
    public void testLogin() {
        //Create a game with no game piece
        List<String> userIDs = new ArrayList<String>(){{
            add("testemail@example.com");
            add("foobar@123.org");
            add("frank.delo.roosy@iampres.com");
            add("tomjones123");
        }};
        
        //Test google+ login serverside
        //The first time, we create the usernames and login
        //second time we login with a name previously found
        for(int i = 0; i < 2; i++) {
            for(String userID : userIDs) {
                Response resp = request("POST", CTX + "/auth/login/" + userID, "{}");
                JSONObject json = new JSONObject(resp.content);
                assertEquals(userID, json.get("userID"));
                JSONObject settings = json.getJSONObject("settings");
                assertEquals(0, settings.get("wins"));
                assertEquals(0, settings.get("losses"));
                assertFalse(Boolean.valueOf(String.valueOf(settings.get("soundSettings"))));
                
                if(i == 0)
                    assertEquals(201, resp.httpStatus);
                else
                    assertEquals(200, resp.httpStatus);
            }
        }

        //Create guest logins
        for(int i = 0; i < 10; i++) {
            Response guestResp = request("POST", CTX + "/auth/login", "{}");
            JSONObject guestJson = new JSONObject(guestResp.content);
            assertEquals("id_" + (userIDs.size() + i), guestJson.get("userID"));
            assertEquals(201, guestResp.httpStatus);
        }
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
