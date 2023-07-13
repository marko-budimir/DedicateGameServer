package org.example.client.ui.model;

import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;
import org.lwjgl.opengl.GL11;

public class Rectangle {
    private Vector2<Float> vertex;
    private float width;
    private float height;
    private Vector3<Float> color;

    public Rectangle(Vector2<Float> vertex, Vector3<Float> color) {
        this.vertex = vertex;
        this.width = 1;
        this.height = 1;
        this.color = color;
    }

    public Rectangle(Vector2<Float> vertex, float size, Vector3<Float> color) {
        this.vertex = vertex;
        this.width = size;
        this.height = size;
        this.color = color;
    }

    public Rectangle(Vector2<Float> vertex, float width, float height, Vector3<Float> color) {
        this.vertex = vertex;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public boolean contains(float x, float y) {
        return (x > vertex.getX() && x < vertex.getX() + this.width &&
                y > vertex.getY() && y < vertex.getY() + this.height);
    }

    public boolean contains(Vector2<Float> point) {
        return contains(point.getX(), point.getY());
    }

    public void setColor(Vector3<Float> color) {
        this.color = color;
    }

    public void setVertex(Vector2<Float> vertex) {
        this.vertex = vertex;
    }

    public void draw() {
        float x = vertex.getX();
        float y = vertex.getY();
        GL11.glColor3f(color.getX(), color.getY(), color.getZ());
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);
        GL11.glEnd();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Rectangle) {
            Rectangle other = (Rectangle) obj;
            return vertex.equals(other.vertex) && width == other.width && height == other.height && color.equals(other.color);
        }
        return false;
    }
}
