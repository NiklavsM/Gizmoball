package model;

import java.util.Set;

public interface IGizmo {
    enum Type {
        Triangle, Wall, Absorber, Square
    }

    Set<Line> getLines();

    Set<Circle> getCircles();

    Circle getRootCircle();

    Circle getEndCircle();

    Type getType();
}
