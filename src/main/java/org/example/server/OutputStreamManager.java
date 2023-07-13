package org.example.server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class OutputStreamManager {
    private static Long IDENTIFIER = 1L;
    private static final OutputStreamManager instance = new OutputStreamManager();
    private final Map<Long, OutputStream> outputStreams;
    private final Map<Long, String> latestMessages;

    private OutputStreamManager() {
        outputStreams = new HashMap<>();
        latestMessages = new HashMap<>();
    }

    public static synchronized OutputStreamManager getInstance() {
        return instance;
    }

    public synchronized void addOutputStream(OutputStream outputStream) {
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println("Welcome to the chat!");
        latestMessages.forEach((key, value) -> out.println(value));

        Long index = getNextIdentifier();
        broadcast(outputStream, "User has joined the chat.");
        outputStreams.put(index, outputStream);
    }

    public synchronized void removeOutputStream(OutputStream outputStream) {
        broadcast(outputStream, "User has left the chat.");
        Long index = getKeysByValue(outputStream);
        outputStreams.remove(index);
    }

    public synchronized void broadcast(OutputStream userOutputStream, String message) {
        for (Map.Entry<Long, OutputStream> entry : outputStreams.entrySet()) {
            OutputStream outputStream = entry.getValue();
            if (outputStream != userOutputStream) {
                PrintWriter out = new PrintWriter(outputStream, true);
                Long index = getKeysByValue(userOutputStream);
                String fullMessage = "User " + index + ": " + message;
                out.println(fullMessage);
                latestMessages.put(index, fullMessage);
            }
        }
    }

    private Long getKeysByValue(OutputStream value) {
        return outputStreams.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(IDENTIFIER - 1);
    }

    private static synchronized long getNextIdentifier() {
        return IDENTIFIER++;
    }
}
