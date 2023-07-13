package org.example.client.ui.screen;


import org.example.client.communication.ServerCollector;
import org.example.client.communication.ServerLocation;
import org.example.client.structure.Vector3;
import org.example.client.ui.listener.MouseListener;
import org.example.client.ui.model.Button;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorScene implements Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    private List<Button> buttons = new ArrayList<>();
    private ServerCollector serverCollector;

    public ServerSelectorScene() {
        System.out.println("ServerSelectorScene");
    }

    @Override
    public void init(int width, int height) {
        serverCollector = new ServerCollector(buttons);
        serverCollector.start();
        Window.instance.r = 1;
        Window.instance.g = 1;
        Window.instance.b = 1;
    }

    @Override
    public void update(float dt) {
        buttons.forEach(Button::draw);

        buttons.forEach(button -> {
            if (button.contains(MouseListener.getX(), MouseListener.getY())) {
                button.setColor(Vector3.of(0.15f, 0.15f, 0.15f));
            } else {
                button.setColor(Vector3.of(0.0f, 0.0f, 0.0f));
            }
        });

        if (MouseListener.mouseButtonDown(0)) {
            buttons.forEach(button -> {
                if (button.contains(MouseListener.getX(), MouseListener.getY())) {
                    changingScene = true;
                    serverCollector.stopCollecting();

                    ServerLocation serverLocation = button.getServerLocation();
                    Window.setServerLocation(serverLocation);
                }
            });
        }

        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.instance.r -= dt;
            Window.instance.g -= dt;
            Window.instance.b -= dt;
        } else if (changingScene) {
            Window.changeScene(1);
        }
    }
}
