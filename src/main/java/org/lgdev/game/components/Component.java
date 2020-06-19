package org.lgdev.game.components;

import org.lgdev.game.engine.GameObject;

public abstract class Component {

    public GameObject gameObject = null;

    public abstract void update(float deltaTime);

    public abstract void start();

    public void renderUI() {

    }
}
