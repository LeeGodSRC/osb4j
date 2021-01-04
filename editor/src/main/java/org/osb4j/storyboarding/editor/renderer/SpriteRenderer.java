package org.osb4j.storyboarding.editor.renderer;

import imgui.ImGui;
import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.osb4j.storyboarding.editor.model.EditorComponent;
import org.osb4j.storyboarding.editor.model.EditorSprite;
import org.osb4j.storyboarding.editor.model.Transform;

@Getter
public class SpriteRenderer extends EditorComponent {

    private Vector4f color = new Vector4f(1, 1, 1, 1);;
    private EditorSprite sprite = new EditorSprite();

    @Setter
    private transient boolean dirty = true;
    private transient Transform lastTransform;

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTextureCoords() {
        return sprite.getTextureCoords();
    }

    public SpriteRenderer setSprite(EditorSprite sprite) {
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
    public void init() {
        this.lastTransform = this.getParent().getTransform().clone();
    }

    @Override
    public void update(float deltaTime) {
        if (!this.lastTransform.equals(this.getParent().getTransform())) {
            dirty = true;
            this.lastTransform.copy(this.getParent().getTransform());
        }
    }

    @Override
    public void renderUI() {
        ImGui.begin("Inspector");

        float[] color = {this.color.x, this.color.y, this.color.z, this.color.w};
        if (ImGui.colorPicker4("Color Picker: ", color))
        {
            this.setColor(new Vector4f(color[0], color[1], color[2], color[3]));
        }

        ImGui.end();
    }

    public Transform getTransform() {
        return this.getParent().getTransform();
    }

}
