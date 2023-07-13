package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Chat server.
 * <p>
 *     Usage: ChatServer &lt;port number&gt;
 * </p>
 * <p>
 *     The chat server serves as a platform for sending messages to all connected clients.
 *     It utilizes the port number to make the chat server's presence known to the network,
 *     allowing clients to establish connections.
 *     Additionally, the server listens on the specified port number to receive incoming connections from clients.
 * </p>
 * <p>
 *     The chat server will broadcast its port number every 5 seconds.
 * </p>
 * <p>
 *     The chat server will listen for incoming connections and create a new thread for each connection.
 * </p>
 *
 *     @see BroadcastServer
 *     @see ChatServerThread
 *     @see OutputStreamManager
 *     @see org.example.client.ChatClient
 */

public class ChatServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: ChatServer <port number>");
            System.exit(-1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;
        new BroadcastServer(portNumber).start();

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
