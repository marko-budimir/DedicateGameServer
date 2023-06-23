package org.example.server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT_NUMBER = 4445;
    private static final List<InetAddress> users = new ArrayList<>();

    public static void main(String[] args) {

        boolean listening = true;
        byte[] buf = new byte[256];

        try (DatagramSocket serverSocket = new DatagramSocket(PORT_NUMBER)) {
            new BroadcastServer().start();
            while (listening) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                serverSocket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                int userId = users.indexOf(address);
                if (userId == -1) {
                    users.add(address);
                    userId = users.indexOf(address);
                }
                broadCast("User " + (userId + 1) + ": " + received, serverSocket);
            }
        } catch (SocketException e) {
            System.out.println("Could not open socket");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Could not receive packet");
            System.exit(-1);
        }
    }

    private static void broadCast(String message, DatagramSocket socket) {
        try {
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.getBytes().length, address, PORT_NUMBER);
            socket.send(packet);
        } catch (UnknownHostException e) {
            System.out.println("Could not find host");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Could not send packet");
            System.exit(-1);
        }
    }
}
