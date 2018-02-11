package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Gizmo implements IGizmo {
    private final String id;
    List<Circle> circles;
    Set<Line> lines;

    public Gizmo(String id) {
        this.circles = new ArrayList<>();
        this.lines = new HashSet<>();
        this.id = id;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public Set<Line> getLines() {
        return lines;
    }

    public String getId() {
        return id;
    }

    public abstract Type getType();

}
