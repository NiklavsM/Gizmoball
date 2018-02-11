package model;

import java.util.List;
import java.util.Set;

public interface IGizmo {
    enum Type {
        Triangle, Wall, Absorber, Square
    }

    Set<Line> getLines();

    List<Circle> getCircles();

    Type getType();
}
