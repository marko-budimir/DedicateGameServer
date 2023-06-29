package org.example.server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OutputStreamManager {
    private static final OutputStreamManager instance = new OutputStreamManager();
    private final List<OutputStream> outputStreams;

    private OutputStreamManager() {
        outputStreams = new ArrayList<>();
    }

    public static synchronized OutputStreamManager getInstance() {
        return instance;
    }

    public synchronized void addOutputStream(OutputStream outputStream) {
        outputStreams.add(outputStream);
        broadcast(outputStream, "User has joined the chat.");
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println("Welcome to the chat!");
    }

    public synchronized void removeOutputStream(OutputStream outputStream) {
        broadcast(outputStream, "User has left the chat.");
        outputStreams.remove(outputStream);
    }

    public synchronized void broadcast(OutputStream userOutputStream, String message) {
        int userIndex = outputStreams.indexOf(userOutputStream);
        for (OutputStream outputStream : outputStreams) {
            if (outputStream != userOutputStream) {
                PrintWriter out = new PrintWriter(outputStream, true);
                out.println("User " + (userIndex + 1) + ": " + message);
            }
        }
    }
}
