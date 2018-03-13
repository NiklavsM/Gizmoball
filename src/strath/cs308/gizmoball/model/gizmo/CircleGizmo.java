package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;

public class CircleGizmo extends AbstractTriggerAndTriggarableGizmo {

    public CircleGizmo(double x, double y, String id) {
        super(x, y, x + 1, y + 1, id);

        Circle circle = new Circle(x + 0.5, y + 0.5, 0.5);
        rotatingPoint = circle.getCenter();
    }

    public CircleGizmo(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        circles.add(new Circle(x1 + 0.5, y1 + 0.5, 0.5));

    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.CIRCLE;
    }
}
