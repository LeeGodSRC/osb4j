package org.osb4j.storyboarding.editor.util;

import imgui.ImGui;
import imgui.ImVec2;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.osb4j.storyboarding.editor.Editor;

@UtilityClass
public class PositionUtil {

    public static ImVec2 getLargestSizeForViewport(float aspectRatio) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float aspectWidth = windowSize.x;
        float aspectHeight = aspectWidth / aspectRatio;
        if (aspectHeight > windowSize.y) {
            // We must switch to pillarbox mode
            aspectHeight = windowSize.y;
            aspectWidth = aspectHeight * aspectRatio;
        }

        return new ImVec2(aspectWidth, aspectHeight);
    }

    public static ImVec2 getCenteredPositionForViewport(ImVec2 aspectSize) {
        ImVec2 windowSize = new ImVec2();
        ImGui.getContentRegionAvail(windowSize);
        windowSize.x -= ImGui.getScrollX();
        windowSize.y -= ImGui.getScrollY();

        float viewportX = (windowSize.x / 2.0f) - (aspectSize.x / 2.0f);
        float viewportY = (windowSize.y / 2.0f) - (aspectSize.y / 2.0f);

        return new ImVec2(viewportX + ImGui.getCursorPosX(),
                viewportY + ImGui.getCursorPosY());
    }

}
