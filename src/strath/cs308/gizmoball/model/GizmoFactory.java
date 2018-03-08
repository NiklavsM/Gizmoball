package strath.cs308.gizmoball.model;

import mit.physics.Vect;
import strath.cs308.gizmoball.model.gizmo.*;

import java.util.UUID;

public class GizmoFactory implements IGizmoFactory {

    public IGizmo createGizmo(IGizmo.Type type, double x, double y, String id) {
        switch (type) {
            case TRIANGLE:
                return new Triangle(x, y, id);
            case CIRCLE:
                return new CircleGizmo(x, y, id);
            case SQUARE:
                return new Square(x, y, id);
            case BALL:
                return new Ball(x, y, id);
            case ABSORBER:
                return new Absorber(x, y, x + 1, y + 1, id);
            case LEFT_FLIPPER:
                return new Flipper(x, y, Flipper.Orientation.LEFT, id);
            case RIGHT_FLIPPER:
                return new Flipper(x, y, Flipper.Orientation.RIGHT, id); //FIXME
        }
        throw new IllegalArgumentException("no absorber please");
    }

    public IGizmo createGizmo(IGizmo.Type type, double x, double y) {
        return createGizmo(type, x, y, UUID.randomUUID().toString());
    }

    public IGizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2, String id) {
        switch (type) {
            case BALL:
                Ball ball = new Ball(x1, y1, id);
                ball.setVelocity(new Vect(x2, y2));
                return ball;
            case ABSORBER:
                return new Absorber(x1, y1, x2, y2, id);
        }
        throw new IllegalArgumentException("absorber or ball only please:" + type.name());
    }

    @Override
    public IGizmo createGizmo(IGizmo.Type type, double x1, double y1, double x2, double y2) {
        return createGizmo(type, x1, y1, x2, y2, UUID.randomUUID().toString());
    }

}
