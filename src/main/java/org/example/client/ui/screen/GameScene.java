package org.example.client.ui.screen;

import org.example.client.ui.model.Avatar;

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
    }
}
