package org.osb4j.storyboarding.editor;

import lombok.Getter;
import org.lwjgl.Version;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.osb4j.storyboarding.editor.assets.AssetsManager;
import org.osb4j.storyboarding.editor.layer.GUILayer;
import org.osb4j.storyboarding.editor.model.EditorScene;
import org.osb4j.storyboarding.editor.renderer.FrameBuffer;
import org.osb4j.storyboarding.editor.renderer.Renderer;
import org.osb4j.storyboarding.editor.renderer.Shader;
import org.osb4j.storyboarding.editor.util.Utility;
import org.osb4j.storyboarding.editor.view.TimelineView;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Getter
public class Editor implements Runnable {

    public static Editor INSTANCE;

    private int width;
    private int height;
    private String title;

    private long glfwWindow;

    private GUILayer guiLayer;
    private AssetsManager assetsManager;

    private EditorScene scene;

    private TimelineView timelineView;

    private FrameBuffer frameBuffer;

    private Shader defaultShader;
    private Shader pickingShader;

    public void initialize(int width, int height) {
        System.out.println("The osb4j! Editor is currently running LWJGL " + Version.getVersion());

        INSTANCE = this;

        this.width = width;
        this.height = height;
        this.title = "osb4j";

        this.assetsManager = new AssetsManager();

        this.initGLFW();
        this.run();

        this.guiLayer.end();
        this.destroyGLFW();
    }

    @Override
    public void run() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float delta = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);

            // Final Rendering game
            this.tick(delta);

            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            delta = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public void tick(float delta) {
        glViewport(0, 0, 3840, 2160);

        this.frameBuffer.bind();
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        glEnable(GL_BLEND);

        if (delta >= 0) {
            this.scene.update(delta);

            Renderer.bindShader(this.defaultShader);
            this.scene.render();
        }
        this.frameBuffer.unbind();

        this.timelineView.render();

        this.guiLayer.render(this.scene);
    }

    private void destroyGLFW() {
        Callbacks.glfwFreeCallbacks(this.glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void initGLFW() {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW!");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetWindowSizeCallback(this.glfwWindow, (w, width, height) -> {
            this.width = width;
            this.height = height;
        });

        // Callbacks

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        // Framebuffer

        glViewport(0, 0, 3840, 2160);

        // ImGui
        this.guiLayer = new GUILayer(this.glfwWindow);
        this.guiLayer.init();

        // Post Initial
        this.postInitGLFW();
    }

    private void postInitGLFW() {
        Utility.setWindowsIcon(this.glfwWindow,
                "/assets/icon/osu-32x32.png",
                "/assets/icon/osu-16x16.png");

        this.defaultShader = this.assetsManager.getShader("/assets/shader/default.glsl");
        this.pickingShader = this.assetsManager.getShader("/assets/shader/pickingShader.glsl");

        this.scene = new EditorScene();
        this.scene.init();
        this.scene.start();

        this.frameBuffer = new FrameBuffer(3840, 2160);

        this.timelineView = new TimelineView();
        this.timelineView.init();
    }

    public float getTargetAspectRatio() {
        return 16.0f / 9.0f;
    }

}
