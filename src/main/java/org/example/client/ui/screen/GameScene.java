package org.example.client.ui.screen;

import org.example.client.ServerCommunication;
import org.example.client.ServerLocation;
import org.example.client.ui.listener.KeyListener;
import org.example.client.ui.model.Avatar;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class GameScene implements Scene {
    Avatar avatar;
    ServerLocation serverLocation;
    ServerCommunication serverCommunication;

    public GameScene() {
        System.out.println("LevelScene");
    }

    @Override
    public void init(int width, int height) {
        avatar = new Avatar(width, height);
        avatar.draw();
        serverLocation = Window.getServerLocation();
        serverCommunication = new ServerCommunication(serverLocation);
        try {
            serverCommunication.startListening();
        } catch (IOException e) {
            System.err.println("Error while starting communication thread");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(float dt) {
        avatar.update();
        avatar.draw();
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            serverCommunication.stopListening();
            Window.changeScene(0);
        }
    }
}
