package model.gizmo;

import model.Dot;
import model.IDrawable;

import java.util.List;

public interface IEntity extends IDrawable {

    enum Type {
        TRIANGLE("Triangle"), ABSORBER("Absorber"), SQUARE("Square"), BALL("Ball"),
        WALLS("Walls"), FLIPPER("Flipper"), CIRCLE("Circle"),
        LEFT_FLIPPER("LeftFlipper"), RIGHT_FLIPPER("RightFlipper");

        private String name;

        Type(String name) {
            this.name = name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    Type getType();

    String getId();

    List<Dot> getDots();

}
