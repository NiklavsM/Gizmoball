package strath.cs308.gizmoball.model;

import strath.cs308.gizmoball.model.gizmo.*;

import java.util.UUID;

public class GizmoFactory implements IGizmoFactory {

    public Gizmo createGizmo(IGizmo.Type type, double x, double y, String id) {
        switch (type) {
            case Triangle:
                return new Triangle(x, y, id);
            case Circle:
                return new CircleGizmo(x, y, id);
            case Square:
                return new Square(x, y, id);
            case Ball:
                return new Ball(x, y, id);
            case LeftFlipper:
                return new Flipper(x, y,Flipper.Orientation.LEFT, id);
            case RightFlipper:
                return new Flipper(x, y,Flipper.Orientation.RIGHT, id); //FIXME
        }
        throw new IllegalArgumentException("no absorber please");
    }

    public Gizmo createGizmo(IGizmo.Type type, double x, double y) {
        return createGizmo(type, x, y, UUID.randomUUID().toString());
    }

    public Gizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2, String id) {
        switch (type) {
            case Absorber:
                return new Absorber(x1, y1, x2, y2, id);
        }
        throw new IllegalArgumentException("aborsber only please:" + type.name());
    }

}
