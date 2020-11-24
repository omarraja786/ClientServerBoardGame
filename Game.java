package csgame;

import csgame.socket.GameServer;
import csgame.socket.Request;

import java.util.*;


public class Game {
    int[][] storeArray;
    static final int ROWS = 6;
    static final int COLUMNS = 10;
    static final int EMPTY = 0;
    SortedMap<Integer, Integer> totals = new TreeMap<>();
    GameServer gs = new GameServer();
    int MIN_X = 0, MIN_Y = 0;
    int MAX_X =5, MAX_Y =9;
    static ArrayList<int[]> clientCoords = new ArrayList<>();

    //Initialise the board
    public Game() {
        storeArray = new int[ROWS][COLUMNS];
    }

    //Plot random point for each client
    public void plotStart() {
        int currentX = (int) Math.floor(Math.random() * ROWS);
        int currentY = (int) Math.floor(Math.random() * COLUMNS);
        for (int value : gs.returnClientID().values()) {
            storeArray[currentX][currentY] = value;
        }
    }

    public void move(int client, int x, int y){
        if(storeArray[x][y]==client)
            return;
        int startPosX = Math.max(x-1,0);
        int startPosY=Math.max(y-1,0);
        int endPosX = Math.min(x+1,storeArray.length-1);
        int endPosY = Math.min(y+1,storeArray[0].length-1);

        outerloop:
        if (storeArray[x][y] == EMPTY && client <= 5 && client >= 1) {
            for (int rowNum = startPosX; rowNum <= endPosX; rowNum++) {
                for (int colNum = startPosY; colNum <= endPosY; colNum++) {
                    if (storeArray[rowNum][colNum] == client) {
                        storeArray[x][y] = client;
                        int count = totals.getOrDefault(client, 1);
                        totals.put(client, count + 1);
                        break outerloop;
                    }
                }
            }
        }
        System.out.println(totals);
    }

    public void freedom(int client, int x, int y) {
        if (storeArray[x][y] == EMPTY && client <= 5 && client >= 1) {
            storeArray[x][y] = client;
            int count = totals.containsKey(client) ? totals.get(client) : 1;
            totals.put(client, count + 1);
        }
        System.out.println(totals);
    }

    public void replacement(int client, int x, int y) {
        if(storeArray[x][y]==client)
            return;
        int startPosX = Math.max(x-1,0);
        int startPosY=Math.max(y-1,0);
        int endPosX = Math.min(x+1,storeArray.length-1);
        int endPosY = Math.min(y+1,storeArray[0].length-1);

        outerloop:
        if (client <= 5 && client >= 1) {
            for (int rowNum = startPosX; rowNum <= endPosX; rowNum++) {
                for (int colNum = startPosY; colNum <= endPosY; colNum++) {
                    if (storeArray[rowNum][colNum] == client) {
                        storeArray[x][y] = client;
                        int count = totals.getOrDefault(client, 1);
                        totals.put(client, count + 1);
                        break outerloop;
                    }
                }
            }
        }
        System.out.println(totals);
    }

    public void winner(){

    }


    //Return Board
    public String returnBoard() {
        for (int i = 0; i < storeArray.length; i++) {
            for (int j = 0; j < storeArray[i].length; j++) {
            }
        }
        System.out.println(Arrays.deepToString(storeArray).replace("], ", "]\n"));
        System.out.println("____________________________________________________________________");
        return Arrays.deepToString(storeArray);
    }
    public String help() {
        return "Commands: move <id x y>, board, commands, freedom <id x y>, replacement<id x y>";
    }
}
