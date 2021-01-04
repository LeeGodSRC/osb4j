package org.osb4j.storyboarding.editor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class EditorObject {

    private static final AtomicInteger ID_COUNTER = new AtomicInteger();

    private final int id;

    private final String name;
    private final List<EditorComponent> components;
    private transient Transform transform;

    private transient boolean removed;

    public EditorObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.id = ID_COUNTER.getAndIncrement();
    }

    public EditorObject transform(Transform transform) {
        this.transform = transform;
        return this;
    }

    public <T extends EditorComponent> T getComponent(Class<T> type) {
        for (EditorComponent component : this.components) {
            if (type.isInstance(component)) {
                return (T) component;
            }
        }

        return null;
    }

    public <T extends EditorComponent> void removeComponent(Class<T> type) {
        Iterator<EditorComponent> iterator = this.components.iterator();
        while (iterator.hasNext()) {
            if (type.isInstance(iterator.next())) {
                iterator.remove();
                break;
            }
        }
    }

    public void removeComponent(EditorComponent component) {
        this.components.remove(component);
    }

    public EditorObject addComponent(EditorComponent component) {
        if (component instanceof Transform) {
            this.transform = (Transform) component;
        }
        component.generateId();
        this.components.add(component);
        component.setParent(this);
        return this;
    }

    public void update(float delta) {
        this.components.forEach(component -> component.update(delta));
    }

    public void renderUI() {
        this.components.forEach(EditorComponent::renderUI);
    }

    public void destroy() {
        this.components.forEach(EditorComponent::destroy);
    }

    public void init() {
        this.components.forEach(EditorComponent::init);
    }
}
