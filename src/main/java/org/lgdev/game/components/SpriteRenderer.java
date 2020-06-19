package org.lgdev.game.components;

import org.lgdev.game.engine.renderer.Texture;
import imgui.ImGui;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector4f;

@Getter
public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;

    @Setter
    private transient boolean dirty;

    public SpriteRenderer() {
        this.color = new Vector4f();
        this.sprite = new Sprite(null);
        this.dirty = true;
    }

    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
        this.dirty = true;
    }

    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
        this.dirty = true;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTextureCoords() {
        return sprite.getTextureCoords();
    }

    public SpriteRenderer setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.dirty = true;
        return this;
    }

    public SpriteRenderer setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.dirty = true;
            this.color.set(color);
        }
        return this;
    }

    @Override
    public void start() {
    }

    @Override
    public void update(float deltaTime) {
        if (this.gameObject.getTransform().isDirty()) {
            dirty = true;
            this.gameObject.getTransform().setDirty(false);
        }
    }

    @Override
    public void renderUI() {
        float[] color = {this.color.x, this.color.y, this.color.z, this.color.w};
        if (ImGui.colorPicker4("Color Picker: ", color))
        {
            this.setColor(new Vector4f(color[0], color[1], color[2], color[3]));
        }
    }
}
