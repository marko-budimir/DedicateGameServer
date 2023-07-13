package org.example.client.ui.model;

import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;
import org.example.client.ui.listener.KeyListener;

import java.awt.event.KeyEvent;

public class Avatar {
    private float x;
    private float y;
    private final float size;
    private final float speed;
    private final Rectangle square;
    private boolean isMoving;

    public Avatar(int windowWidth, int windowHeight) {
        x = (float) windowWidth / 2;
        y = (float) windowHeight / 2;
        size = 100;
        speed = 5;
        Vector3<Float> color = Vector3.of(0.0f, 1.0f, 0.0f); // green
        square = new Rectangle(Vector2.of(x, y), size, color);
    }

    public void update() {
        if (KeyListener.isKeyPressed(KeyEvent.VK_W)) {
            y -= speed;
            isMoving = true;
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_S)) {
            y += speed;
            isMoving = true;
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_A)) {
            x -= speed;
            isMoving = true;
        }
        if (KeyListener.isKeyPressed(KeyEvent.VK_D)) {
            x += speed;
            isMoving = true;
        }
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void draw() {
        square.setVertex(Vector2.of(x, y));
        square.draw();
        isMoving = false;
    }

    public Vector2<Float> getVertex() {
        return Vector2.of(x, y);
    }

    public float getSize() {
        return size;
    }
}
