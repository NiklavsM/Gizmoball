package model;

public class CircleGizmo extends Gizmo {
    public CircleGizmo(double x, double y, String id) {
        super(id);
        circles.add(new Circle(x + 0.5, y + 0.5, 0.5));
    }

    @Override
    public Type getType() {
        return Type.Circle;
    }
}
