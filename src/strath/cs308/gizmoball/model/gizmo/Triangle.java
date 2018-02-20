package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

import java.util.List;
import java.util.Set;

public class Triangle extends Gizmo {

    public Triangle(double x, double y, String id) {
        super(x, y, x, y, id);

        rotatingPoint = new Vect(x + 0.5, y + 0.5);
    }

    public Triangle(double x, double y) {
        this(x, y, generateID());
    }

    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1, y1, x1 + 1, y1));
        lines.add(new LineSegment(x1, y1, x1, y1 + 1));
        lines.add(new LineSegment(x1 + 1, y1, x1, y1 + 1));

        circles.add(new Circle(x1, y1, 0));
        circles.add(new Circle(x1 + 1, y1, 0));
        circles.add(new Circle(x1, y1 + 1, 0));
    }

    @Override
    public Type getType() {
        return Type.Triangle;
    }

}