package org.lgdev.game.scene;

import org.lgdev.game.engine.Window;

public class GameScene extends Scene {

    public GameScene() {
        System.out.println(2);
        Window.getInstance().rgba(1, 1, 1, 1);
    }

    @Override
    public void update(float deltaTime) {

    }
}
