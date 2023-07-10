package org.example.client.ui.screen;


import org.example.client.ui.listener.KeyListener;
import org.example.client.ui.listener.MouseListener;
import org.example.client.ui.model.Rectangle;
import org.example.client.structure.Vector2;
import org.example.client.structure.Vector3;

import java.awt.event.KeyEvent;

public class ServerSelectorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;
    private Rectangle button;

    public ServerSelectorScene() {
        super();
        System.out.println("LevelEditorScene");
    }

    @Override
    public void init() {
        button = createButton(1280/2, 720/2, 300, 100);
        button.draw();
    }

    private Rectangle createButton( float x, float y, int width, int height) {
        return new Rectangle(Vector2.of(x, y), width, height, Vector3.of(0.0f, 0.0f, 0.0f));
    }

    @Override
    public void update(float dt) {
        button.draw();

        //System.out.println((1.0f / dt) + " FPS");

        if(MouseListener.mouseButtonDown(0)) {
            if(button.contains(MouseListener.getX(), MouseListener.getY())) {
                System.out.println("Button pressed");
                changingScene = true;
            }
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
