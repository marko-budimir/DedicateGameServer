package org.example.client;

import org.example.client.ui.model.Button;

import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerCollector extends Thread {
    private static final int PORT_NUMBER = 4445;
    private Set<ServerLocation> serverLocations;
    private boolean isRunning = true;
    private List<Button> buttons;

    public ServerCollector(List<Button> buttons) {
        serverLocations = new HashSet<>();
        this.buttons = buttons;
    }

    public void stopCollecting() {
        isRunning = false;
    }

    @Override
    public void run() {
        byte[] buf = new byte[256];
        DatagramPacket initialPacket = new DatagramPacket(buf, buf.length);
        int width = 300;
        int height = 100;
        int size = 0;
        while (isRunning) {
            try (DatagramSocket datagramSocket = new DatagramSocket(null)) {
                datagramSocket.setReuseAddress(true);
                datagramSocket.bind(new InetSocketAddress(PORT_NUMBER));
                datagramSocket.receive(initialPacket);
            } catch (SocketException e) {
                System.err.println("Couldn't open datagram socket");
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't receive initial packet");
                System.exit(1);
            }
            InetAddress address = initialPacket.getAddress();
            int port = initialPacket.getPort();

            initialPacket = new DatagramPacket(buf, buf.length, address, port);
            String receivedData = new String(initialPacket.getData(), 0, initialPacket.getLength());
            int portNumber = Integer.parseInt(receivedData.trim());

            ServerLocation serverLocation = new ServerLocation(address, portNumber);
            serverLocations.add(serverLocation);
            System.out.println("Received packet from " + address + ":" + portNumber);
            if (serverLocations.size() > size) {
                size = serverLocations.size();
                Button button = new Button(width, height, size);
                button.setServerLocation(serverLocation);
                buttons.add(button);
            }
        }
    }
}
