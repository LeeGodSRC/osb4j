package org.osb4j.storyboarding.editor.model;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;

import java.util.Objects;

@Getter
@Setter
public class Transform extends EditorComponent implements Cloneable {

    private Vector2f position;
    private Vector2f scale;
    private float rotation = 0.0f;
    private int layerIndex;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
        this.layerIndex = 0;
    }

    public Transform move(float x, float y) {
        this.position.add(x, y);
        return this;
    }

    public Transform pos(float x, float y) {
        this.position.set(x, y);
        return this;
    }

    public Transform scale(float x, float y) {
        this.scale.set(x, y);
        return this;
    }

    public Transform layerIndex(int layerIndex) {
        this.layerIndex = layerIndex;
        return this;
    }

    @Override
    public void renderUI() {

    }

    @Override
    public Transform clone() {
        Transform transform = new Transform();
        transform.position.set(this.position);
        transform.scale.set(this.scale);
        transform.rotation = this.rotation;
        return transform;
    }

    public void copy(Transform transform) {
        this.position.set(transform.getPosition());
        this.scale.set(transform.getScale());
        this.rotation = transform.getRotation();
        this.layerIndex = transform.getLayerIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transform transform = (Transform) o;
        return Float.compare(transform.rotation, rotation) == 0 && layerIndex == transform.layerIndex && Objects.equals(position, transform.position) && Objects.equals(scale, transform.scale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, scale, rotation, layerIndex);
    }
}
