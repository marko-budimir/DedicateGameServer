package org.example.client;

import org.example.client.ui.listener.WindowListener;
import org.example.client.ui.model.Enemy;
import org.example.client.ui.model.Rectangle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import static org.example.client.ui.MessageHandler.decodeMessage;

public class ServerCommunication {
    private final Socket socket;
    private final BufferedReader serverReader;
    private final PrintWriter serverWriter;
    private boolean running = true;

    public ServerCommunication(ServerLocation serverLocation) {
        try {
            socket = new Socket(serverLocation.getAddress(), serverLocation.getPortNumber());
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            closeClient();
            throw new RuntimeException(e);
        }
    }

    public void startListening(Map<String, Rectangle> enemies) throws IOException {
        Thread thread = new Thread(() -> {
            try {
                while (running && WindowListener.isRunning()) {
                    String serverMessage = serverReader.readLine();
                    if (serverMessage != null) {
                        System.out.println(serverMessage);
                        Enemy enemy = decodeMessage(serverMessage);
                        if (enemy != null) {
                            enemies.putIfAbsent(enemy.getClientID(), enemy.getRectangle());
                            enemies.get(enemy.getClientID()).setVertex(enemy.getVertex());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Couldn't read from server");
                closeClient();
                System.exit(1);
            } finally {
                closeClient();
            }
        });
        thread.start();
    }

    public void stopListening() {
        running = false;
    }

    public void sendMessage(String message) {
        serverWriter.println(message);
    }

    private void closeClient() {
        try {
            if (serverReader != null) {
                serverReader.close();
            }
            if (serverWriter != null) {
                serverWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Couldn't close client");
            System.exit(1);
        }
    }
}
