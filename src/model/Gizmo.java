package model;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

public abstract class Gizmo implements IGizmo {

    public enum Type {
        Triangle, Wall, Ball
    }

    private final String name;

    protected List<Circle> circles;
    protected List<LineSegment> lines;

    public Gizmo(String name) {
        this.circles = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.name = name;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<LineSegment> getLines() {
        return lines;
    }

    public abstract void rotate(int degrees);

    public abstract Gizmo.Type getType();

}



