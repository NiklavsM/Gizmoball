package strath.cs308.gizmoball.model;

import mit.physics.Circle;

import java.util.List;
import java.util.Set;

public interface IGizmo {
    enum Type {
        TRIANGLE, WALL, ABSORBER, SQUARE, CIRCLE, LEFT_FLIPPER, RIGHT_FLIPPER, BALL;
    }

    Set<Line> getLines();

    List<Circle> getCircles();

    Type getType();

    String getId();
}
