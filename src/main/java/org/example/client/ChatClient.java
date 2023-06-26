package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ChatClient {
    private static final int PORT_NUMBER = 4445;
    private final DatagramSocket socket;
    private final BufferedReader stdIn;

    public ChatClient() {
        try {
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(PORT_NUMBER));
            stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server");
            closeClient();
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        ChatClient client = new ChatClient();
        client.start();
    }

    public void start() {
        byte[] buf = new byte[256];
        DatagramPacket initialPacket = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(initialPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InetAddress address = initialPacket.getAddress();
        int port = initialPacket.getPort();
        System.out.println("Connected to server at " + address + ":" + port);


        Thread serverThread = new Thread(() -> {
            boolean running = true;
            try {
                while (running) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    if (!received.isEmpty()) {
                        System.out.println(received);
                    }
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
                    DatagramPacket packet = new DatagramPacket(
                            input.getBytes(),
                            input.getBytes().length,
                            address,
                            port);
                    socket.send(packet);
                }
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host");
                closeClient();
                System.exit(1);
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
        if (socket != null && stdIn != null) {
            try {
                stdIn.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
