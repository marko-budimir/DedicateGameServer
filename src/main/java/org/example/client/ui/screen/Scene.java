package org.example.client.ui.screen;

public interface Scene {
    public void init(int width, int height);

    public abstract void update(float dt);
}
