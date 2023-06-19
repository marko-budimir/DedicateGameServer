package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: ChatServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new ChatServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
