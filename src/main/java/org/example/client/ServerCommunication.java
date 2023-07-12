package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public void startListening() throws IOException {
        Thread thread = new Thread(() -> {
            try {
                while (running) {
                    String serverMessage = serverReader.readLine();
                    if (serverMessage != null) {
                        System.out.println(serverMessage);
                    }
                }
            } catch (IOException e) {
                System.err.println("Couldn't read from server");
                closeClient();
                throw new RuntimeException(e);
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