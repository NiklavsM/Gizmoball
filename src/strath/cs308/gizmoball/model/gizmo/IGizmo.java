package strath.cs308.gizmoball.model.gizmo;

import mit.physics.LineSegment;
import strath.cs308.gizmoball.model.Dot;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IGizmo {

    List<Dot> getDots();

    Set<LineSegment> getLines();

    Type getType();

    String getId();

    void rotate();

    void move(double x, double y);

    double getStartX();

    double getStartY();

    double getEndX();

    double getEndY();

    void setReflectionCoefficient(double coefficient);

    double getReflectionCoefficient();

    String getColor();

    boolean setColor(String color);

    boolean overlapsWithAnyGizmos(Collection<IGizmo> gizmos);

    enum Type {
        TRIANGLE("Triangle"), ABSORBER("Absorber"), SQUARE("Square"), BALL("Ball"),
        WALLS("Walls"), FLIPPER("Flipper"), CIRCLE("Circle"),
        LEFT_FLIPPER("LeftFlipper"), RIGHT_FLIPPER("RightFlipper"),
        RHOMBUS("Rhombus"),OCTAGON("Octagon");

        private String name;

        Type(String name) {
            this.name = name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

}
