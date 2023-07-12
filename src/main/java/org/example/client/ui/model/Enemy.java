package org.example.client.ui.model;

import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;

public class Enemy {
    private final String clientID;
    private final float x;
    private final float y;
    private final float size;

    public Enemy(String clientID, float x, float y, float size) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Rectangle getRectangle() {
        Vector2<Float> vertex = Vector2.of(x, y);
        Vector3<Float> color = Vector3.of(1.0f, 0.0f, 0.0f); // red
        return new Rectangle(vertex, size, color);
    }

    public Vector2<Float> getVertex() {
        return Vector2.of(x, y);
    }

    public String getClientID() {
        return clientID;
    }

}
