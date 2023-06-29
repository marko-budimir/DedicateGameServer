package org.example.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BroadcastServer extends Thread {
    private static final long SLEEP_TIME = 5000;
    private static final int PORT_NUMBER = 4445;
    private DatagramSocket socket;
    private boolean running = true;
    private final int portNumber;

    public BroadcastServer(final int portNumber) {
        super("BroadcastServer");
        this.portNumber = portNumber;
        try {
            this.socket = new DatagramSocket();
            socket.setBroadcast(true);
        } catch (SocketException e) {
            System.err.println("Couldn't open socket");
            System.exit(-1);
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                byte[] buf = String.valueOf(portNumber).getBytes();
                InetAddress address = InetAddress.getByName("255.255.255.255");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT_NUMBER);
                socket.send(packet);
            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }

            try {
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
