package org.lgdev.game.components;

import org.lgdev.game.engine.renderer.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Texture texture;
    private List<Sprite> sprites;

    private int spriteWidth, spriteHeight, numSprites, spacing;

    public SpriteSheet() {
        this.sprites = new ArrayList<>();
    }

    public SpriteSheet texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public SpriteSheet spriteWidth(int width) {
        this.spriteWidth = width;
        return this;
    }

    public SpriteSheet spriteHeight(int height) {
        this.spriteHeight = height;
        return this;
    }

    public SpriteSheet numSprites(int numSprites) {
        this.numSprites = numSprites;
        return this;
    }

    public SpriteSheet spacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    public SpriteSheet build() {
        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;

        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float) texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float) texture.getWidth();
            float leftX = currentX / (float) texture.getWidth();
            float bottomY = currentY / (float) texture.getHeight();

            Vector2f[] texCoords = new Vector2f[] {
                    new Vector2f(rightX, topY),
                    new Vector2f(rightX, bottomY),
                    new Vector2f(leftX, bottomY),
                    new Vector2f(leftX, topY)
            };

            Sprite sprite = new Sprite(texture).textureCoords(texCoords);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
        return this;
    }

    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

}
