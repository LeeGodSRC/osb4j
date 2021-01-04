package org.osb4j.storyboarding.beatmap.section;

import lombok.Getter;
import org.osb4j.storyboarding.beatmap.Beatmap;
import org.osb4j.storyboarding.exception.BeatmapFormatException;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BeatmapSection {

    protected static final char SEPARATOR = ':';

    private final String name;
    private final Map<PropertyKey, Object> properties;

    private final Beatmap beatmap;

    public BeatmapSection(Beatmap beatmap, String name) {
        this.beatmap = beatmap;

        this.name = name;
        this.properties = new HashMap<>();
    }

    protected void addProperty(String key, PropertyType type, Object object) {
        this.properties.put(new PropertyKey(key, type), object);
    }

    public <T> T getProperty(String key) {
        return (T) this.properties.getOrDefault(key, null);
    }

    public void load(Iterable<String> lines) {
        for (String line : lines) {
            for (PropertyKey key : this.properties.keySet()) {
                if (line.startsWith(key.getName())) {
                    try {
                        this.properties.put(key, key.getType().convert(this.getValue(line)));
                    } catch (Throwable throwable) {
                        throw new BeatmapFormatException(beatmap, throwable, "Error while trying to parse map property");
                    }
                    break;
                }
            }
        }
    }

    private String getValue(String line) {
        if (line.trim().charAt(line.length() - 1) != SEPARATOR) {
            String s = line.substring(line.indexOf(SEPARATOR) + 1);
            return s.trim();
        }
        return "";
    }

}
