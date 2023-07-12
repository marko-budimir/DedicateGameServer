package org.example.client.ui.model;

import org.example.client.ServerLocation;
import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;

public class Button {
    private ServerLocation serverLocation;
    private final Rectangle rectangle;

    public Button(int width, int height, int position) {
        this.rectangle = new Rectangle(
                Vector2.of(
                        (float) 1280 / 2 - (float) width / 2,
                        (float) height / 2 + height * (position - 1) * 1.1f
                ),
                width,
                height,
                Vector3.of(0.0f, 0.0f, 0.0f)
        );
    }

    public void setColor(Vector3<Float> color) {
        rectangle.setColor(color);
    }

    public boolean contains(float x, float y) {
        return rectangle.contains(x, y);
    }

    public void draw() {
        rectangle.draw();
    }

    public ServerLocation getServerLocation() {
        return serverLocation;
    }

    public void setServerLocation(ServerLocation serverLocation) {
        this.serverLocation = serverLocation;
    }
}
