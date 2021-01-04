package org.osb4j.storyboarding.exception;

import org.osb4j.storyboarding.beatmap.Beatmap;

public class BeatmapFormatException extends IllegalArgumentException {

    private Beatmap beatmap;

    public BeatmapFormatException(Beatmap beatmap) {
        this.beatmap = beatmap;
    }

    public BeatmapFormatException(Beatmap beatmap, String message) {
        super(message);

        this.beatmap = beatmap;
    }

    public BeatmapFormatException(Beatmap beatmap, Throwable throwable, String message) {
        super(message, throwable);

        this.beatmap = beatmap;
    }

}
