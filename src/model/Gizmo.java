package model;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.LineSegment;

import java.util.HashSet;
import java.util.Set;

public abstract class Gizmo implements IGizmo {

    private final String name;
    protected Set<Circle> circles;
    protected Set<LineSegment> lines;

    public Gizmo(String name) {
        this.circles = new HashSet<>();
        this.lines = new HashSet<>();
        this.name = name;
    }

    public Gizmo() {
        this("randomName"); // FIXME
    }

    public Set<Circle> getCircles() {
        return circles;
    }

    public Set<LineSegment> getLines() {
        return lines;
    }

    public abstract void rotate(Angle angle);

    public abstract Gizmo.Type getType();

    public enum Type {
        Triangle, Wall, Ball, Flipper, Square
    }

}



