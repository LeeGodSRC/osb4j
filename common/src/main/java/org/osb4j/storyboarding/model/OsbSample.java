package org.osb4j.storyboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OsbSample implements OsbObject {

    private String path;

    private double time;
    private double volume;

    @Override
    public double startTime() {
        return this.time;
    }

    @Override
    public double endTime() {
        return this.time;
    }

    @Override
    public void write() {

    }
}
