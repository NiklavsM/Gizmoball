package model;

import physics.Circle;

import java.util.List;
import java.util.Set;

public interface IGizmo {
    enum Type {
        Triangle, Wall, Absorber, Square, Circle, LeftFlipper, RightFlipper, Ball;
    }

    Set<Line> getLines();

    List<Circle> getCircles();

    Type getType();

    String getId();
}
