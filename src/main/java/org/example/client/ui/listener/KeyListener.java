package org.example.client.ui.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    public static final KeyListener instance = new KeyListener();
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (key < instance.keyPressed.length) {
            if (action == GLFW_PRESS) {
                instance.keyPressed[key] = true;
            } else if (action == GLFW_RELEASE) {
                instance.keyPressed[key] = false;
            }
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < instance.keyPressed.length) {
            return instance.keyPressed[keyCode];
        } else {
            return false;
        }
    }

    public static boolean isKeyReleased(int keyCode) {
        if (keyCode < instance.keyPressed.length) {
            return !instance.keyPressed[keyCode];
        } else {
            return false;
        }
    }

    public static void endFrame() {
        for (int i = 0; i < instance.keyPressed.length; i++) {
            instance.keyPressed[i] = false;
        }
    }

    public static void endFrame(int keyCode) {
        if (keyCode < instance.keyPressed.length) {
            instance.keyPressed[keyCode] = false;
        }
    }

    public static void endFrame(int[] keyCodes) {
        for (int keyCode : keyCodes) {
            if (keyCode < instance.keyPressed.length) {
                instance.keyPressed[keyCode] = false;
            }
        }
    }
}
