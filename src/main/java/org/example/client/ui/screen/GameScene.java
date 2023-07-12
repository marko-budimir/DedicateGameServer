package org.example.client.ui.screen;

import org.example.client.ServerCommunication;
import org.example.client.ServerLocation;
import org.example.client.structure.Vector2;
import org.example.client.ui.listener.KeyListener;
import org.example.client.ui.model.Avatar;
import org.example.client.ui.model.Rectangle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.example.client.ui.MessageHandler.encodeMessage;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class GameScene implements Scene {
    private Avatar avatar;
    private ServerCommunication serverCommunication;
    private final Map<String, Rectangle> enemies = new HashMap<>();


    public GameScene() {
        System.out.println("LevelScene");
    }

    @Override
    public void init(int width, int height) {
        avatar = new Avatar(width, height);
        avatar.draw();

        ServerLocation serverLocation;
        serverLocation = Window.getServerLocation();
        serverCommunication = new ServerCommunication(serverLocation);
        try {
            serverCommunication.startListening(enemies);
        } catch (IOException e) {
            System.err.println("Error while starting communication thread");
            e.printStackTrace();
            System.exit(1);
        }

        sendAvatarPosition();
    }

    @Override
    public void update(float dt) {
        enemies.values().forEach(Rectangle::draw);
        avatar.update();
        if (avatar.isMoving()) {
            sendAvatarPosition();
        }
        avatar.draw();
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            serverCommunication.stopListening();
            Window.changeScene(0);
        }
    }

    private void sendAvatarPosition() {
        Vector2<Float> vertex = avatar.getVertex();
        float size = avatar.getSize();
        serverCommunication.sendMessage(encodeMessage(vertex.getX(), vertex.getY(), size));
    }
}
