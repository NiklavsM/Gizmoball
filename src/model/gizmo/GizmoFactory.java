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

    public IEntity createEntity(IEntity.Type type, double x, double y, String id) {
        switch (type) {
            case TRIANGLE:
                return new Triangle(x, y, id);
            case CIRCLE:
                return new CircleGizmo(x, y, id);
            case SQUARE:
                return new Square(x, y, id);
            case LEFT_FLIPPER:
                return new Flipper(x, y,Flipper.Orientation.LEFT, id);
            case RIGHT_FLIPPER:
                return new Flipper(x, y,Flipper.Orientation.RIGHT, id); //FIXME
        }
        throw new IllegalArgumentException("no absorber nor ball please");
    }

    public IEntity createEntity(IEntity.Type type, double x1, double y1, double x2, double y2, String id) {
        switch (type) {
            case BALL:
                return new Ball(x1, y1, x2, y2, id);
            case ABSORBER:
                return new Absorber(x1, y1, x2, y2, id);
        }
        throw new IllegalArgumentException("aborsber or ball only please");
    }

}
