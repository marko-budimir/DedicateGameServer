package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class ChatClient {
    private static final int PORT_NUMBER = 4445;
    private final Socket socket;
    private final BufferedReader serverReader;
    private final PrintWriter serverWriter;
    private final BufferedReader stdIn;

    public ChatClient(InetAddress hostName, int portNumber) {
        try {
            socket = new Socket(hostName, portNumber);
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new PrintWriter(socket.getOutputStream(), true);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            closeClient();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        byte[] buf = new byte[256];
        DatagramPacket initialPacket = new DatagramPacket(buf, buf.length);
        try (DatagramSocket datagramSocket = new DatagramSocket(null)) {
            datagramSocket.setReuseAddress(true);
            datagramSocket.bind(new InetSocketAddress(PORT_NUMBER));
            datagramSocket.receive(initialPacket);
        } catch (SocketException e) {
            System.err.println("Couldn't open datagram socket");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't receive initial packet");
        }
        InetAddress address = initialPacket.getAddress();
        int port = initialPacket.getPort();

        initialPacket = new DatagramPacket(buf, buf.length, address, port);
        String receivedData = new String(initialPacket.getData(), 0, initialPacket.getLength());
        int portNumber = Integer.parseInt(receivedData.trim());


        ChatClient client = new ChatClient(address, portNumber);
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
            } finally {
                closeClient();
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
            } finally {
                closeClient();
            }
        });
        clientThread.start();
    }

    private void closeClient() {
        try {
            if (serverReader != null) {
                serverReader.close();
            }
            if (serverWriter != null) {
                serverWriter.close();
            }
            if (stdIn != null) {
                stdIn.close();
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
