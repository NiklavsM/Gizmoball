package model;

import physics.*;

public class Triangle extends Gizmo {

    public Triangle(int x, int y) {
        super("name"); //TODO fix
        circles.add(new Circle(x, y, 0));
        circles.add(new Circle(x, y + 1, 0));
        circles.add(new Circle(x + 1, y, 0));
        lines.add(new LineSegment(x, y, x, y + 1));
        lines.add(new LineSegment(x, y, x + 1, y));
        lines.add(new LineSegment(x, y + 1, x + 1, y));
    }

    @Override
    public void rotate(Angle angle) { // FIXME not tested
        lines
                .forEach(line -> line = Geometry.rotateAround(line,
                        line.p2().minus(line.p1()).times(0.5),
                        angle));
    }

    @Override
    public Type getType() {
        return Type.Triangle;
    }
}
