package com.oose2015.sselvar4.hareandhounds;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.util.HashMap;

/**
 * Created by Sindhuula Selvaraju.
 * The purpose of this class it to save the positions of the different pieces of the board for corresponding gameId and moveNo
 */
public class HnHBoard {
    public String game_id;
    public String move_no;
    public String x1, x2, x3, x4;
    public String y1, y2, y3, y4;
//Constructor for the Board positions
    public HnHBoard(String game_id, String move_no, String x1, String y1, String x2, String y2, String x3, String y3, String x4, String y4)
    {
        this.game_id = game_id;
        this.move_no = move_no;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
    }
//Converts the position of each piece into a string and returns an array of strings
    public String[] convert()
    {
        String[] convRes = {x1+y2,x2+y2,x3+y3,x4+y4};
        return convRes;
    }
}
