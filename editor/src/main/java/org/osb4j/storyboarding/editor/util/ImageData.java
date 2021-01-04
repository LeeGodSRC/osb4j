package org.osb4j.storyboarding.editor.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
@AllArgsConstructor
public class ImageData {

    private int width;
    private int height;
    private ByteBuffer byteBuffer;

}
