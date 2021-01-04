package org.osb4j.storyboarding.model;

import lombok.Getter;
import org.osb4j.storyboarding.model.command.Command;

import java.util.List;

@Getter
public class OsbSprite implements OsbObject {

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final Position DEFAULT_POSITION = new Position(320, 240);

    private OsbType type;
    private OsbLayer layer;

    private OsbOrigin origin;
    private String file;

    private Position position;
    private List<Command> commands;

    private double startTime;
    private double endTime;

    @Override
    public double startTime() {
        return this.startTime;
    }

    @Override
    public double endTime() {
        return this.endTime;
    }

    @Override
    public void write() {

    }
}
