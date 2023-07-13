package org.example.client.ui.listener;

public class WindowListener {
    public static final WindowListener instance = new WindowListener();
    private boolean isRunning = true;

    private WindowListener() {
    }

    public static void windowCloseCallback(long window) {
        System.out.println("Window closed");
        instance.isRunning = false;
    }

    public static boolean isRunning() {
        return instance.isRunning;
    }
}
