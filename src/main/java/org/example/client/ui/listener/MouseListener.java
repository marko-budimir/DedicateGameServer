package org.example.client.ui.listener;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    public static final MouseListener instance = new MouseListener();
    private double xPos;
    private double yPos;
    private final boolean[] mouseButtonPressed = new boolean[3];

    private MouseListener() {
        this.xPos = 0.0;
        this.yPos = 0.0;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        instance.xPos = xPos;
        instance.yPos = yPos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < instance.mouseButtonPressed.length) {
                instance.mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < instance.mouseButtonPressed.length) {
                instance.mouseButtonPressed[button] = false;
            }
        }
    }

    public static float getX() {
        return (float) instance.xPos;
    }

    public static float getY() {
        return (float) instance.yPos;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < instance.mouseButtonPressed.length) {
            return instance.mouseButtonPressed[button];
        } else {
            return false;
        }
    }
}
