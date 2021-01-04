package org.osb4j.storyboarding.editor.util;

import lombok.experimental.UtilityClass;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Objects;

@UtilityClass
public class Utility {

    public static void setWindowsIcon(long window, String... paths) {
        BufferedImage[] images = new BufferedImage[paths.length];
        for (int i = 0; i < paths.length; i++) {
            try {
                images[i] = ImageIO.read(Utility.class.getResourceAsStream(paths[i]));
            } catch (Throwable throwable) {
                throw new IllegalStateException("Something wrong while loading icon " + paths[i]);
            }
        }

        GLFWImage[] glfwImages = imagesToGLFWImages(images);
        try (final GLFWImage.Buffer iconSet = GLFWImage.malloc(images.length)) {

            for (int i = images.length - 1; i >= 0; i--) {
                final GLFWImage image = glfwImages[i];
                iconSet.put(i, image);
            }

            GLFW.glfwSetWindowIcon(window, iconSet);
        }
    }

    private static GLFWImage[] imagesToGLFWImages(final BufferedImage[] images) {

        final GLFWImage[] out = new GLFWImage[images.length];

        for (int i = 0; i < images.length; i++) {
            final BufferedImage image = images[i];
            out[i] = imageToGLFWImage(image);
        }

        return out;
    }

    private static GLFWImage imageToGLFWImage(BufferedImage image) {

        ByteBuffer byteBuffer = imageToByteBuffer(image);

        final GLFWImage result = GLFWImage.create();
        result.set(image.getWidth(), image.getHeight(), byteBuffer);

        return result;
    }

    public static ImageData readImage(String path) {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Utility.class.getResourceAsStream(path)));

            return new ImageData(image.getWidth(), image.getHeight(), imageToByteBuffer(image));
        } catch (Throwable throwable) {
            throw new RuntimeException("Something wrong while loading image from " + path, throwable);
        }
    }

    public static ByteBuffer imageToByteBuffer(BufferedImage image) {
        if (image.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {

            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            final Graphics2D graphics = convertedImage.createGraphics();

            final int targetWidth = image.getWidth();
            final int targetHeight = image.getHeight();

            graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();

            image = convertedImage;
        }

        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorSpace = image.getRGB(j, i);
                buffer.put((byte) ((colorSpace << 8) >> 24));
                buffer.put((byte) ((colorSpace << 16) >> 24));
                buffer.put((byte) ((colorSpace << 24) >> 24));
                buffer.put((byte) (colorSpace >> 24));
            }
        }

        buffer.flip();
        return buffer;
    }

}
