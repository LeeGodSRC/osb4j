package org.osb4j.storyboarding.editor.assets;

import org.osb4j.storyboarding.editor.renderer.Shader;
import org.osb4j.storyboarding.editor.renderer.Texture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetsManager {

    private final Map<String, Shader> shaders = new ConcurrentHashMap<>(1);
    private final Map<String, Texture> textures = new ConcurrentHashMap<>(1);

    public Shader getShader(String resourceName) {
        if (this.shaders.containsKey(resourceName)) {
            return this.shaders.getOrDefault(resourceName, null);
        }

        Shader shader = new Shader(resourceName);
        shader.compile();
        this.shaders.put(resourceName, shader);

        return shader;
    }

    public Texture getTexture(String resourceName) {
        if (this.textures.containsKey(resourceName)) {
            return this.textures.getOrDefault(resourceName, null);
        }

        Texture texture = new Texture();
        texture.init(resourceName);
        this.textures.put(resourceName, texture);

        return texture;
    }

}
