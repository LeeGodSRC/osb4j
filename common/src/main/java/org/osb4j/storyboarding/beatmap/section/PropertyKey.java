package org.osb4j.storyboarding.beatmap.section;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PropertyKey {

    private final String name;
    private final PropertyType type;

}
