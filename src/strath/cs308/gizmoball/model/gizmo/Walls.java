package strath.cs308.gizmoball.model.gizmo;

import mit.physics.LineSegment;

public class Walls extends Gizmo {

    public Walls() {
        super(0, 0, 20, 20, "OuterWalls");
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);
        lines.add(new LineSegment(0, 0, 0, 20));
        lines.add(new LineSegment(0, 0, 20, 0));
        lines.add(new LineSegment(0, 20, 20, 20));
        lines.add(new LineSegment(20, 0, 20, 20));
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.WALLS;
    }

    @Override
    public void move(double x, double y) {
        throw new UnsupportedOperationException();
    }
}
