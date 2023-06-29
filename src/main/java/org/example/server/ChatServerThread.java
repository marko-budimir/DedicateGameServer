package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ChatServerThread extends Thread {
    private final Socket socket;
    private final OutputStreamManager outputStreamManager;

    public ChatServerThread(Socket socket) {
        super("ChatServerThread");
        this.socket = socket;
        outputStreamManager = OutputStreamManager.getInstance();
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            OutputStream outputStream = socket.getOutputStream();
            outputStreamManager.addOutputStream(outputStream);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                outputStreamManager.broadcast(outputStream, inputLine);
            }
            outputStreamManager.removeOutputStream(outputStream);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
