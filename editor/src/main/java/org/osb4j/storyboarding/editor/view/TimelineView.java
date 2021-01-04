package org.osb4j.storyboarding.editor.view;

import imgui.ImGui;
import imgui.ImVec2;
import org.osb4j.storyboarding.editor.Editor;
import org.osb4j.storyboarding.editor.renderer.FrameBuffer;
import org.osb4j.storyboarding.editor.util.DrawUtil;
import org.osb4j.storyboarding.editor.util.PositionUtil;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class TimelineView {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720 / 2;

    private FrameBuffer frameBuffer;

    public void init() {
        this.frameBuffer = new FrameBuffer(WIDTH, HEIGHT);
    }

    public void render() {
        this.frameBuffer.bind();

        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        Editor.INSTANCE.getDefaultShader().use();
        DrawUtil.drawRect(0, 0, 64, 64);
        Editor.INSTANCE.getDefaultShader().detach();

        this.frameBuffer.unbind();
    }

    public void renderUI() {
        ImGui.begin("Timeline");

        ImVec2 windowSize = PositionUtil.getLargestSizeForViewport(WIDTH / HEIGHT);
        ImVec2 windowPos = PositionUtil.getCenteredPositionForViewport(windowSize);

        ImGui.setCursorPos(windowPos.x, windowPos.y);

        int textureId = this.frameBuffer.getTextureId();
        ImGui.image(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);

        ImGui.end();
    }

}
