package model.gizmo;

import model.Dot;

import java.util.List;

public interface IGizmo {

    enum Type {
        Triangle, Absorber, Square, Ball, Walls, Flipper, Circle
    }

    List<Dot> getDots();

    Type getType();

    String getId();

    void rotate();
}
