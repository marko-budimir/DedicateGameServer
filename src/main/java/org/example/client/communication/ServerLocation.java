package org.example.client.communication;

import java.net.InetAddress;
import java.util.Objects;

public class ServerLocation {
    private final InetAddress address;
    private final int portNumber;

    public ServerLocation(final InetAddress address, final int portNumber) {
        this.address = address;
        this.portNumber = portNumber;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPortNumber() {
        return portNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, portNumber);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ServerLocation)) return false;
        ServerLocation other = (ServerLocation) obj;
        return Objects.equals(address, other.address) && Objects.equals(portNumber, other.portNumber);
    }
}
