package org.osb4j.storyboarding.beatmap;

import org.osb4j.storyboarding.exception.BeatmapFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Beatmap {

    public static final String OSU_FILE_FORMAT = "osu file format v";

    public static final Pattern SECTION_PATTERN = Pattern.compile("(?<=\\[).+?(?=\\])");

    private int fileVersion;

    public void load(String... lines) {

        if (lines[0].startsWith(OSU_FILE_FORMAT)) {
            try {
                this.fileVersion = Integer.parseInt(lines[0].split(OSU_FILE_FORMAT)[1]);
            } catch (NumberFormatException ex) {
                throw new BeatmapFormatException(this, ex, "Error while trying to parse Osu File Format");
            }
        }

        int section = -1;
        List<String> sectionLines = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = SECTION_PATTERN.matcher(lines[i]);

            if (matcher.find()) {
                if (section != -1) {
                    this.loadSection(section, sectionLines);
                    sectionLines.clear();
                }

                section = 1;
            }
        }
    }

    public void loadSection(int section, List<String> lines) {

    }

}
