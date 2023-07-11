package org.example.client.ui.screen;

import org.example.client.ui.listener.KeyListener;
import org.example.client.ui.model.Avatar;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class GameScene extends Scene {
    Avatar avatar;

    public GameScene() {
        super();
        System.out.println("LevelScene");
    }

    @Override
    public void init() {
        avatar = new Avatar(1280, 720);
        avatar.draw();
    }

    public void update(float dt) {
        avatar.update();
        avatar.draw();
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            Window.changeScene(0);
        }
    }
}
