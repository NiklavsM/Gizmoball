package model.gizmo;


import physics.Circle;

public class CircleGizmo extends Gizmo {

    public CircleGizmo(double x, double y, String id) {
        super(id);
        Circle circle = new Circle(x + 0.5, y + 0.5, 0.5);
        circles.add(new Circle(x + 0.5, y + 0.5, 0.5));
        rotatingPoint = circle.getCenter();
    }

    public CircleGizmo (double x, double y) {
        this(x, y, generateID());
    }

    @Override
    public Type getType() {
        return Type.Circle;
    }
}
