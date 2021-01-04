package org.osb4j.storyboarding.editor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public abstract class EditorComponent {

    private static final AtomicInteger ID_COUNTER = new AtomicInteger();

    private int id = -1;

    private transient EditorObject parent = null;

    public void init() {

    }

    public void update(float dt) {

    }

    public void renderUI() {

    }

    public void destroy() {

    }

    public final void generateId() {
        if (this.id == -1) {
            this.id = ID_COUNTER.getAndIncrement();
        }
    }

    public Transform getTransform() {
        return this.parent.getTransform();
    }

}
