package org.osb4j.storyboarding.editor.renderer;

import org.osb4j.storyboarding.editor.model.EditorObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Renderer {

    private static Shader CURRENT_SHADER;
    private final int MAX_BATCH_SIZE = 1000;
    private Map<Integer, List<RenderBatch>> batches;

    public Renderer() {
        this.batches = new TreeMap<>(Integer::compareTo);
    }

    public static void bindShader(Shader shader) {
        CURRENT_SHADER = shader;
    }

    public static Shader getBoundShader() {
        return CURRENT_SHADER;
    }

    public void add(EditorObject editorObject) {
        SpriteRenderer renderer = editorObject.getComponent(SpriteRenderer.class);
        if (renderer != null) {
            this.add(renderer);
        }
    }

    private void add(SpriteRenderer renderer) {
        boolean added = false;
        boolean contains = false;
        Texture texture = renderer.getTexture();
        int index = renderer.getParent().getTransform().getLayerIndex();

        if (this.batches.containsKey(index)) {
            contains = true;
            for (RenderBatch batch : this.batches.get(index)) {
                if (batch.hasRoom() && batch.zIndex() == index) {
                    if (texture == null || (batch.hasTexture(texture) || batch.hasTextureRoom())) {
                        batch.addSprite(renderer);
                        added = true;
                        break;
                    }
                }
            }
        }

        if (!added) {
            RenderBatch batch = new RenderBatch(MAX_BATCH_SIZE, index, this);
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
        CURRENT_SHADER.use();
        for (List<RenderBatch> batches : this.batches.values()) {
            for (RenderBatch batch : batches) {
                batch.render();
            }
        }
    }
}
