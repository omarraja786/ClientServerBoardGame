package csgame.socket;

import csgame.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameServer {
    public static final int PORT = 8888;
    public static int maxConnections = 1;
    public static ArrayList socketValue = new ArrayList();
    public static SortedMap<Integer, Integer> clients = new TreeMap<>();

    public static void main(String args[]){
        Game game = new Game();
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

        while(maxConnections < 6){
            try {
                socket = serverSocket.accept();
                System.out.println("Connection Established");
                GameService service = new GameService(game, socket);
                Thread t = new Thread(service);
                t.start();
                System.out.println("Client #" + socket.getPort() + " has connected.");
                clients.put(socket.getPort(), maxConnections);
                socketValue.add(socket.getPort());
                maxConnections++;
                game.plotStart();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection Error");
            }
        }
        if(maxConnections > 6) {
            System.out.print("Max number of connections reached.");
        }
    }

    public SortedMap<Integer, Integer> returnClientID() {
        return clients;
    }

    public int returnPlayerID() {
        return maxConnections;
    }

    public ArrayList returnSocketID() {
        return socketValue;
    }
}