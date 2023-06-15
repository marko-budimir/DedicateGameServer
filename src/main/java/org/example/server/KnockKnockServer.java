package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KnockKnockServer {
    public static void main(String[] args) throws IOException{

        if (args.length != 1) {
            System.err.println("Usage: KnockKnockServer <port number>");
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                new KKMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
