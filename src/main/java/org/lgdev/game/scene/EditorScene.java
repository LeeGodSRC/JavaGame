package org.lgdev.game.scene;

import org.lgdev.game.components.SpriteRenderer;
import org.lgdev.game.components.SpriteSheet;
import org.lgdev.game.engine.Camera;
import org.lgdev.game.engine.GameObject;
import org.lgdev.game.engine.Transform;
import org.lgdev.game.engine.Window;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lgdev.game.utIl.AssetPool;

public class EditorScene extends Scene {

    public EditorScene() {
        System.out.println(1);
    }

    @Override
    public void init() {
        Window.getInstance().rgba(1, 1, 1, 1);

        this.loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1;
        this.addGameObject(obj1 = new GameObject("Object 1", new Transform()
                .pos(100, 100)
                .scale(256, 256))
                .zIndex(2)
                .addComponent(new SpriteRenderer()
                    .setColor(new Vector4f(1, 0, 0, 0.5f)
                    ))
        );
        this.activeGameObject = obj1;

        this.addGameObject(new GameObject("Object 2", new Transform()
                .pos(300, 100)
                .scale(256, 256))
                .zIndex(1)
                .addComponent(new SpriteRenderer()
                        .setColor(new Vector4f(0, 0, 1, 0.5f)
                        ))
        );
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpriteSheet("assets/images/spritesheet.png", new SpriteSheet()
            .texture(AssetPool.getTexture("assets/images/spritesheet.png"))
            .spriteWidth(16)
            .spriteHeight(16)
            .numSprites(26)
            .spacing(0)
            .build());
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("FPS: " + (1.0f / deltaTime));

        this.updateObjects(deltaTime);
        this.getRenderer().render();
    }

    @Override
    public void renderUI() {
        ImGui.begin("你好");
        ImGui.text("Some random text");
        ImGui.end();
    }
}
