package org.example.client.ui.screen;


import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;
import org.example.client.ui.listener.MouseListener;
import org.example.client.ui.model.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ServerSelectorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    private List<Rectangle> buttons = new ArrayList<>();

    public ServerSelectorScene() {
        super();
        System.out.println("LevelEditorScene");
    }

    @Override
    public void init() {
        int width = 300;
        int height = 100;
        for (int i = 0; i < 5; i++) {
            buttons.add(createButton(
                    1280 / 2 - (float) width / 2,
                    (float) height / 2 + height * i * 1.1f,
                    300,
                    100
            ));
        }
    }

    private Rectangle createButton(float x, float y, int width, int height) {
        return new Rectangle(Vector2.of(x, y), width, height, Vector3.of(0.0f, 0.0f, 0.0f));
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
