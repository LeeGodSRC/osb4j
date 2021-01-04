package org.osb4j.storyboarding.editor.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joml.Vector2f;
import org.osb4j.storyboarding.editor.Editor;
import org.osb4j.storyboarding.editor.renderer.Texture;

@Getter
@Setter
@NoArgsConstructor
public class EditorSprite {

    private float width;
    private float height;
    private Texture texture;
    private Vector2f[] textureCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public EditorSprite(String texturePath) {
        this.texture = Editor.INSTANCE.getAssetsManager().getTexture(texturePath);
        this.width = this.texture.getWidth();
        this.height = this.texture.getHeight();
    }

    public EditorSprite textureCoords(Vector2f... textureCoords) {
        this.textureCoords = textureCoords;
        return this;
    }

}
