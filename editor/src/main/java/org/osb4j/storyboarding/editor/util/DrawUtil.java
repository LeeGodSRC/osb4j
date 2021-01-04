package org.osb4j.storyboarding.editor.util;

import lombok.experimental.UtilityClass;
import org.joml.Vector4f;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public class DrawUtil {

    public static void drawRect(float x, float y, float width, float height) {
        glColor4f(0, 1, 0, 1);
        glBegin(GL_QUADS);

        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);

        glEnd();
    }

}
