package csgame;

import csgame.socket.GameServer;
import csgame.socket.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static csgame.Game.COLUMNS;
import static csgame.Game.ROWS;
import static csgame.socket.GameServer.PORT;
import static csgame.socket.GameServer.maxConnections;
import static junit.framework.TestCase.*;



public class Tests {
    int[][] storeArray;

    @Test
    public void testServerConnection() throws Exception {
        GameServer server = new GameServer();
        assertNotNull(server);
    }

    @Test
    public void testSocket() throws Exception {
        Socket socket = null;
        ServerSocket serverSocket = null;
        System.out.println("Waiting for Clients to Connect...");
        try{
            serverSocket = new ServerSocket(PORT);

        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Server error");
        }
        socket = serverSocket.accept();
        assertNotNull(socket);
    }

    @Test
    public void textMaxConnections() throws Exception {
        int maxConnections = 5;
        int minConnections = 2;

        assert(maxConnections <=5 && minConnections >=2);

    }

    @Test
    public void testRandomStart() throws Exception {
        storeArray = new int[ROWS][COLUMNS];
        int currentX = (int) Math.floor(Math.random() * ROWS);
        int currentY = (int) Math.floor(Math.random() * COLUMNS);
        storeArray = new int[currentX][currentY];
        assertNotNull(storeArray);
    }
}
