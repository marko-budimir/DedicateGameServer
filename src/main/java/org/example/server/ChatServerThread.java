package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ChatServerThread extends Thread {
    private Socket socket = null;
    private SocketManager socketManager = null;

    public ChatServerThread(Socket socket) {
        super("ChatServerThread");
        this.socket = socket;
        socketManager = SocketManager.getInstance();
    }

    public void run() {

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            OutputStream outputStream = socket.getOutputStream();
            socketManager.addOutputStream(outputStream);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                socketManager.broadcast(outputStream, inputLine);
            }
            socketManager.removeOutputStream(outputStream);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
