package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;

public class Square extends Gizmo {

    public Square(int x, int y, String name) {
        super(name);

        lines.add(new LineSegment(x, y, x + 1, y));
        lines.add(new LineSegment(x, y, x, y + 1));
        lines.add(new LineSegment(x, y + 1, x + 1, y + 1));
        lines.add(new LineSegment(x + 1, y + 1, x + 1, y));

        circles.add(new Circle(x, y, 0));
        circles.add(new Circle(x, y + 1, 0));
        circles.add(new Circle(x + 1, y, 0));
        circles.add(new Circle(x + 1, y + 1, 0));
    }

    public Square(int x, int y) {
        this(x, y, "RandomName"); // FIXME
    }

    @Override
    public void rotate(Angle angle) {

    }

    @Override
    public Type getType() {
        return Type.Square;
    }
}
