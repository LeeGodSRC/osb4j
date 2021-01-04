package org.osb4j.storyboarding.editor.layer;

import imgui.*;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import org.osb4j.storyboarding.editor.Editor;
import org.osb4j.storyboarding.editor.model.EditorScene;
import org.osb4j.storyboarding.editor.view.EditorView;
import org.osb4j.storyboarding.editor.view.TimelineView;

import static org.lwjgl.glfw.GLFW.*;

public class GUILayer {

    private final long glfwWindow;

    // LWJGJ3 window backend
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    // LWJGL3 renderer (SHOULD be initialized)
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private EditorView editorView;

    public GUILayer(long glfwWindow) {
        this.glfwWindow = glfwWindow;
        this.editorView = new EditorView();
    }

    public void init() {
        // IMPORTANT!!
        // This line is critical for Dear ImGui to work.
        ImGui.createContext();

        // ------------------------------------------------------------
        // Initialize ImGuiIO config
        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null); // We don't want to save .ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
//        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows

        io.setConfigViewportsNoTaskBarIcon(true);

        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        // Glyphs could be added per-font as well as per config used globally like here
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());

        fontConfig.setName("segoeui.ttf, 16px"); // We can apply a new config value every time we add a new font
        fontAtlas.addFontFromFileTTF("editor/src/main/resources/assets/font/segoeui.ttf", 16, fontConfig);

        fontConfig.destroy(); // After all fonts were added we don't need this config more

        // When viewports are enabled we tweak WindowRounding/WindowBg so platform windows can look identical to regular ones.
        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final ImGuiStyle style = ImGui.getStyle();
            style.setWindowRounding(0.0f);
            style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1));
        }

        imGuiGlfw.init(this.glfwWindow, true);
        imGuiGl3.init("#version 330 core");
    }

    public void render(EditorScene scene) {
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        this.renderDockSpace();

        // render
        scene.renderUI();
        Editor.INSTANCE.getTimelineView().renderUI();
        this.editorView.render();

        ImGui.end();
        ImGui.render();
        this.endFrame();
    }

    private void renderDockSpace() {
        int windowFlags = ImGuiWindowFlags.MenuBar | ImGuiWindowFlags.NoDocking;

        ImGui.setNextWindowPos(0.0f, 0.0f, ImGuiCond.Always);
        ImGui.setNextWindowSize(Editor.INSTANCE.getWidth(), Editor.INSTANCE.getHeight());

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0.0f);
        windowFlags |= ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoBringToFrontOnFocus | ImGuiWindowFlags.NoNavFocus;

        ImGui.begin("Dockspace", new ImBoolean(true), windowFlags);
        ImGui.popStyleVar(2);

        // Dock Space
        ImGui.dockSpace(ImGui.getID("Dockspace"));
    }

    private void endFrame() {
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    public void end() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();

        ImGui.destroyContext();

    }

}
