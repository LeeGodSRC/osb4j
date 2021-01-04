package org.osb4j.storyboarding.editor.view;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiWindowFlags;
import org.osb4j.storyboarding.editor.Editor;
import org.osb4j.storyboarding.editor.util.PositionUtil;

public class EditorView {

    public void render() {
        ImGui.begin("Editor View", ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoScrollWithMouse | ImGuiWindowFlags.MenuBar);

        ImVec2 windowSize = PositionUtil.getLargestSizeForViewport(Editor.INSTANCE.getTargetAspectRatio());
        ImVec2 windowPos = PositionUtil.getCenteredPositionForViewport(windowSize);

        ImGui.setCursorPos(windowPos.x, windowPos.y);

        int textureId = Editor.INSTANCE.getFrameBuffer().getTextureId();
        ImGui.image(textureId, windowSize.x, windowSize.y, 0, 1, 1, 0);

        ImGui.end();
    }

}
