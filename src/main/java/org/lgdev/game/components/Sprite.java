package org.lgdev.game.components;

import org.lgdev.game.engine.renderer.Texture;
import lombok.Getter;
import org.joml.Vector2f;

@Getter
public class Sprite {

    private Texture texture;
    private Vector2f[] textureCoords;

    public Sprite(Texture texture) {
        this.texture = texture;
        this.textureCoords = new Vector2f[]{
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
    }

    public Sprite textureCoords(Vector2f... textureCoords) {
        this.textureCoords = textureCoords;
        return this;
    }
}
