package model.gizmo;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class Triangle extends Gizmo {

    public Triangle(double x, double y, String id) {
        super(id);
        lines.add(new LineSegment(x, y, x + 1, y));
        lines.add(new LineSegment(x, y, x, y + 1));
        lines.add(new LineSegment(x + 1, y, x, y + 1));

        circles.add(new Circle(x, y, 0));
        circles.add(new Circle(x + 1, y, 0));
        circles.add(new Circle(x, y + 1, 0));

        rotatingPoint = new Vect(x + 0.5, y + 0.5);
    }

    public Triangle(double x, double y) {
        this(x, y, generateID());
    }


    @Override
    public Type getType() {
        return Type.Triangle;
    }

}
