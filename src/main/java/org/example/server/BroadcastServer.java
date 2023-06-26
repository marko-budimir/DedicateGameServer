package org.example.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BroadcastServer extends Thread {
    private static final long SLEEP_TIME = 10000;
    private static final int PORT_NUMBER = 4445;
    private final DatagramSocket socket;
    private boolean running = true;

    public BroadcastServer(DatagramSocket socket) throws SocketException {
        super("BroadcastServer");
        this.socket = socket;
    }

    public void run() {
        while (running) {
            try {
                byte[] buf = new byte[0];
                InetAddress address = InetAddress.getByName("255.255.255.255");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT_NUMBER);
                socket.send(packet);

                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                running = false;
                e.printStackTrace();
            }
        }
    }
}
