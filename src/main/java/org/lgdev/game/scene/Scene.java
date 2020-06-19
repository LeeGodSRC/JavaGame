package org.lgdev.game.scene;

import org.lgdev.game.engine.Camera;
import org.lgdev.game.engine.GameObject;
import org.lgdev.game.engine.renderer.Renderer;
import imgui.ImGui;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Scene {

    private Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean running = false;
    private List<GameObject> gameObjects = new ArrayList<>();
    protected  GameObject activeGameObject;

    public Scene() {

    }

    public abstract void update(float deltaTime);

    public void init() {

    }

    public void start() {

        for (GameObject gameObject : this.gameObjects) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
        this.running = true;

    }

    public void updateObjects(float deltaTime) {
        for (GameObject gameObject : this.gameObjects) {
            gameObject.update(deltaTime);
        }
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);

        if (this.running) {
            gameObject.start();
            this.renderer.add(gameObject);
        }
    }

    public void sceneUI() {
        if (activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.renderUI();
            ImGui.end();
        }

        this.renderUI();
    }

    public void renderUI() {

    }
}
