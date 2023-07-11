package org.example.client.ui.screen;


import org.example.client.structure.Vector3;
import org.example.client.ui.ServerCollector;
import org.example.client.ui.listener.MouseListener;
import org.example.client.ui.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    private List<Rectangle> buttons = new ArrayList<>();
    private ServerCollector serverCollector;

    public ServerSelectorScene() {
        super();
        System.out.println("ServerSelectorScene");
    }

    @Override
    public void init() {
        serverCollector = new ServerCollector(buttons);
        serverCollector.start();
        Window.instance.r = 1;
        Window.instance.g = 1;
        Window.instance.b = 1;
    }

    @Override
    public void update(float dt) {
        buttons.forEach(Rectangle::draw);

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
