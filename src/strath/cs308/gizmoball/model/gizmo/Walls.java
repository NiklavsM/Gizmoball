package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;

import java.util.List;
import java.util.Set;

public class Walls extends Gizmo {


    public Walls() {
        super(0, 0, 20, 20, "OuterWalls");
    }


    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(0, 0, 0, 20));
        lines.add(new LineSegment(0, 0, 20, 0));
        lines.add(new LineSegment(0, 20, 20, 20));
        lines.add(new LineSegment(20, 0, 20, 20));
    }

    @Override
    public Type getType() {
        return Type.WALLS;
    }
}