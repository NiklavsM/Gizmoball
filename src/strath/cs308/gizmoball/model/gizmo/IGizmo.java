package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.Dot;

import java.util.List;

public interface IGizmo {

    enum Type {
        TRIANGLE, ABSORBER, SQUARE, BALL, WALLS, FLIPPER, CIRCLE, LEFT_FLIPPER, RIGHT_FLIPPER
    }

    List<Dot> getDots();

    Type getType();

    String getId();

    void rotate();
}
