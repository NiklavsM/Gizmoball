package model.gizmo;

import model.Ball;
import model.Flipper;

public class GizmoFactory {

    private static final GizmoFactory factory = new GizmoFactory();

    private GizmoFactory() {

    }

    public static GizmoFactory getInstance() {
        return factory;
    }

    public IGizmo createGizmo(IGizmo.Type type, double x, double y, String id) {
        switch (type) {
            case Triangle:
                return new Triangle(x, y, id);
            case Circle:
                return new CircleGizmo(x, y, id);
            case Square:
                return new Square(x, y, id);
            case LeftFlipper:
                return new Flipper(x, y,Flipper.Orientation.LEFT, id);
            case RightFlipper:
                return new Flipper(x, y,Flipper.Orientation.RIGHT, id); //FIXME
        }
        throw new IllegalArgumentException("no absorber nor ball please");
    }

    public IGizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2, String id) {
        switch (type) {
            case Ball:
                return new Ball(x1, y1, x2, y2, id);
            case Absorber:
                return new Absorber(x1, y1, x2, y2, id);
        }
        throw new IllegalArgumentException("aborsber or ball only please");
    }

}
