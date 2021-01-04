package org.osb4j.storyboarding.editor.renderer;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.osb4j.storyboarding.editor.util.ImageData;
import org.osb4j.storyboarding.editor.util.Utility;

import javax.rmi.CORBA.Util;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

@Getter
public class Texture {
    private String filepath;
    private transient int id;
    private int width, height;

    public Texture() {
        id = -1;
        width = -1;
        height = -1;
    }

    public Texture(int width, int height) {
        this.filepath = "Generated";

        // Generate texture on GPU
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    }

    public void init(String filepath) {
        this.filepath = filepath;

        // Generate texture on GPU
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        // When stretching the image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        // When shrinking an image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        ImageData imageData = Utility.readImage(filepath);

        this.width = imageData.getWidth();
        this.height = imageData.getHeight();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.width, this.height,
                0, GL_RGB, GL_UNSIGNED_BYTE, imageData.getByteBuffer());
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}