package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Chat server thread.
 * <p>
 *     The chat server thread listens for incoming connections using the specified port number and
 *     broadcasts messages to all connected clients.
 * </p>
 *
 * @see Thread
 * @see BroadcastServer
 * @see ChatServer
 * @see OutputStreamManager
 */
public class ChatServerThread extends Thread {
    private final Socket socket;

    public ChatServerThread(Socket socket) {
        super("ChatServerThread");
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamManager.getInstance().addOutputStream(outputStream);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                OutputStreamManager.getInstance().broadcast(outputStream, inputLine);
            }
            OutputStreamManager.getInstance().removeOutputStream(outputStream);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
