package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private Socket socket;
    private BufferedReader serverReader;
    private PrintWriter serverWriter;
    private BufferedReader stdIn;

    public ChatClient(String hostName, int portNumber) {
        try {
            socket = new Socket(hostName, portNumber);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("Usage: ChatClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(hostName, portNumber);
        client.start();
    }

    public void start() {
        Thread serverThread = new Thread(() -> {
            try {
                String message;
                while ((message = serverReader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.err.println("Couldn't get I/O from server");
                closeClient();
                System.exit(1);
            }
        });
        serverThread.start();

        Thread clientThread = new Thread(() -> {
            try {
                String input;
                while ((input = stdIn.readLine()) != null) {
                    serverWriter.println(input);
                }
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to server");
                closeClient();
                System.exit(1);
            }
        });
        clientThread.start();
    }


    private void closeClient() {
        try {
            stdIn.close();
            serverWriter.close();
            serverReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
