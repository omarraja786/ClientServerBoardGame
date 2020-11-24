package csgame.socket;

import csgame.Game;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class GameService implements Runnable {
    private Scanner in;
    private PrintWriter out;
    private String customer;
    private boolean login;
    private Game game;

    public GameService(Game game, Socket socket) {
        this.game = game;
        customer = null;
        login = false;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = Request.parse(in.nextLine());
                String response = execute(game, request);
                // note use of \r\n for CRLF
                out.println(response + "\r\n");
            } catch (NoSuchElementException e) {
                login = false;
            }
        }
    }


    public String execute(Game game, Request request) {
        GameServer gs = new GameServer();
        Game g = new Game();
        if(gs.returnPlayerID() >= 3) {
            try {
                switch (request.type) {
                    case MOVE:
                        int clientID = Integer.parseInt(request.params[0]);
                        int getX = Integer.parseInt(request.params[1]);
                        int getY = Integer.parseInt(request.params[2]);
                        game.move(clientID, getX, getY);
                        if(clientID > 5 || clientID <1) {
                            out.println("Client ID must be between 1 and 4");
                        }
                        return game.returnBoard();
                    case BOARD:
                        return game.returnBoard();
                    case INVALID:
                        return "Command invalid or failed!";
                    case COMMANDS:
                        return game.help();
                    case FREEDOM:
                        clientID = Integer.parseInt(request.params[0]);
                        getX = Integer.parseInt(request.params[1]);
                        getY = Integer.parseInt(request.params[2]);
                        game.freedom(clientID, getX, getY);
                        if(clientID > 5 || clientID <1) {
                            out.println("Client ID must be between 1 and 4");
                        }
                        return game.returnBoard();
                    case REPLACEMENT:
                        clientID = Integer.parseInt(request.params[0]);
                        getX = Integer.parseInt(request.params[1]);
                        getY = Integer.parseInt(request.params[2]);
                        game.replacement(clientID, getX, getY);
                        if(clientID > 5 || clientID <1) {
                            out.println("Client ID must be between 1 and 4");
                        }
                        return game.returnBoard();
                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
        else {
            out.println("Must have 2 players connected to execute command");
        }
        return "";
    }
}
