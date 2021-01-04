package org.osb4j.storyboarding.editor.model;

import imgui.ImGui;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.osb4j.storyboarding.editor.Editor;
import org.osb4j.storyboarding.editor.renderer.Renderer;
import org.osb4j.storyboarding.editor.renderer.SpriteRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class EditorScene {

    private Camera camera;
    private Renderer renderer;
    private boolean running;
    private List<EditorObject> objects;

    private EditorObject selectedObject;

    public EditorScene() {
        this.renderer = new Renderer();
        this.objects = new ArrayList<>();
        this.running = false;
    }

    public EditorObject createObject(String name) {
        EditorObject object = new EditorObject(name);
        object.addComponent(new Transform());
        return object;
    }

    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));

        EditorObject object = new EditorObject("Object 2").transform(new Transform()
                .pos(1, 1)
                .scale(100f, 100f)
                .layerIndex(1))
                .addComponent(new SpriteRenderer()
                        .setColor(new Vector4f(0, 0, 1, 0.2f)
                        ));

        EditorObject object2 = new EditorObject("Object 1").transform(new Transform()
                .pos(20, 20)
                .scale(100f, 100f)
                .layerIndex(2))
                .addComponent(new SpriteRenderer()
                        .setColor(new Vector4f(0, 1, 0, 0.2f)
                        ));

        this.selectedObject = object;
        this.addObject(object);
        this.addObject(object2);
    }

    public void start() {
        this.objects.forEach(this::initObject);
        this.running = true;
    }

    public void addObject(EditorObject object) {
        this.objects.add(object);
        if (this.isRunning()) {
            this.initObject(object);
        }
    }

    public void destroy() {
        this.objects.forEach(EditorObject::destroy);
    }

    public EditorObject getObject(int id) {
        return null;
    }

    public void update(float delta) {
        Iterator<EditorObject> iterator = this.objects.iterator();
        while (iterator.hasNext()) {
            EditorObject object = iterator.next();

            object.update(delta);
            if (object.isRemoved()) {
                iterator.remove();
            }
        }

    }

    public void render() {
        this.renderer.render();
    }

    public void renderUI() {
        ImGui.begin("Test");

        ImGui.button("lol");

        ImGui.end();

        if (this.selectedObject != null) {
            this.selectedObject.renderUI();
        }
    }

    private void initObject(EditorObject object) {
        object.init();
        this.renderer.add(object);
    }

}
