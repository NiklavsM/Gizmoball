package model.gizmo;

import model.Dot;
import model.IDrawable;

import java.util.List;

public interface IEntity extends IDrawable {

    enum Type {
        TRIANGLE, ABSORBER, SQUARE, BALL, WALLS, FLIPPER, CIRCLE, LEFT_FLIPPER, RIGHT_FLIPPER
    }

    Type getType();

    String getId();

    List<Dot> getDots();

}
