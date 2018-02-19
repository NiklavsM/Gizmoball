package model.gizmo;

import model.Dot;
import model.IDrawable;

import java.util.List;

public interface IEntity extends IDrawable {

    enum Type {
        Triangle, Absorber, Square, Ball, Walls, Flipper, Circle, LeftFlipper, RightFlipper
    }

    Type getType();

    String getId();

    List<Dot> getDots();

}
