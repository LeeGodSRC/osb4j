package org.osb4j.storyboarding.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Position {

    private float x;
    private float y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Position add(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

}
