package org.lgdev.game.engine.renderer;

import org.lgdev.game.components.SpriteRenderer;
import org.lgdev.game.engine.GameObject;

import java.util.*;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private Map<Integer, List<RenderBatch>> batches;

    public Renderer() {
        this.batches = new TreeMap<>(Integer::compareTo);
    }

    public void add(GameObject gameObject) {
        SpriteRenderer renderer = gameObject.getComponent(SpriteRenderer.class);
        if (renderer != null) {
            this.add(renderer);
        }
    }

    private void add(SpriteRenderer renderer) {
        boolean added = false;
        boolean contains = false;
        Texture texture = renderer.getTexture();
        int index = renderer.gameObject.getZIndex();

        if (this.batches.containsKey(index)) {
            contains = true;
            for (RenderBatch batch : this.batches.get(index)) {
                if (batch.isHasRoom()
                        && texture != null
                        && (batch.isHasTextureRoom()
                        || batch.hasTexture(texture))) {
                    batch.addSprite(renderer);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch batch = new RenderBatch(MAX_BATCH_SIZE, index);
            batch.start();

            if (contains) {
                batches.get(index).add(batch);
            } else {
                List<RenderBatch> batches = new ArrayList<>();
                batches.add(batch);

                this.batches.put(index, batches);
            }
            batch.addSprite(renderer);
        }
    }

    public void render() {
        for (List<RenderBatch> batches : this.batches.values()) {
            for (RenderBatch batch : batches) {
                batch.render();
            }
        }
    }
}
