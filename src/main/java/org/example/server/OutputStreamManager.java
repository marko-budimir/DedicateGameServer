package org.example.server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Output stream manager.
 * <p>
 *     The output stream manager is a singleton used in a chat server to handle output streams.
 *     It performs multiple functions, such as broadcasting messages to all connected clients
 *     and managing the addition and removal of output streams.
 *     Essentially, it facilitates communication and organization within the chat server by handling these responsibilities.
 * </p>
 *
 * @see OutputStream
 * @see PrintWriter
 * @see HashMap
 * @see Map
 * @see BroadcastServer
 * @see ChatServer
 * @see ChatServerThread
 */
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

    /**
     * Add output stream.
     * <p>
     *     The add output stream method is used to add an output stream to the output stream manager.
     * </p>
     *
     * @param outputStream The output stream to add.
     *
     * @see OutputStream
     * @see PrintWriter
     * @see HashMap
     */
    public synchronized void addOutputStream(OutputStream outputStream) {
        PrintWriter out = new PrintWriter(outputStream, true);
        out.println("Welcome to the chat!");
        latestMessages.forEach((key, value) -> out.println(value));

        Long index = getNextIdentifier();
        broadcast(outputStream, "User has joined the chat.");
        outputStreams.put(index, outputStream);
    }

    /**
     * Remove output stream.
     * <p>
     *     The remove output stream method is used to remove an output stream from the output stream manager.
     * </p>
     *
     * @param outputStream The output stream to remove.
     * @see OutputStream
     * @see PrintWriter
     * @see HashMap
     * @see Map
     */
    public synchronized void removeOutputStream(OutputStream outputStream) {
        broadcast(outputStream, "User has left the chat.");
        Long index = getKeysByValue(outputStream);
        outputStreams.remove(index);
    }

    /**
     * Broadcast.
     * <p>
     *     The broadcast method is used to broadcast a message to all connected clients.
     * </p>
     *
     * @param userOutputStream The output stream of the user sending the message.
     * @param message The message to broadcast.
     *
     * @see OutputStream
     * @see PrintWriter
     * @see HashMap
     * @see Map
     */
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

    /**
     * Get keys by value.
     * <p>
     *     The get keys by value method is used to get the client index by output stream value.
     * </p>
     * @param value The output stream value.
     * @return Long
     */
    private Long getKeysByValue(OutputStream value) {
        return outputStreams.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(IDENTIFIER - 1);
    }

    /**
     * Get next identifier.
     * <p>
     *     The get next identifier method is used to get the next client identifier.
     * </p>
     *
     * @return Long
     */
    private static synchronized long getNextIdentifier() {
        return IDENTIFIER++;
    }
}
