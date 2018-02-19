package strath.cs308.gizmoball.model.gizmo;

import strath.cs308.gizmoball.model.Dot;

import java.util.List;

public interface IGizmo {

    enum Type {
        Triangle, Absorber, Square, Ball, Walls, Flipper, Circle, LeftFlipper, RightFlipper
    }

    List<Dot> getDots();

    Type getType();

    String getId();

    void rotate();
}
