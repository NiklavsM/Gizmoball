package model.gizmo;

import physics.LineSegment;

public class Walls extends Gizmo {

    public Walls() {
        super("OuterWalls");
        lines.add(new LineSegment(0, 0, 0, 20));
        lines.add(new LineSegment(0, 0, 20, 0));
        lines.add(new LineSegment(0, 20, 20, 20));
        lines.add(new LineSegment(20, 0, 20, 20));
    }

    @Override
    public Type getType() {
        return Type.Walls;
    }
}
