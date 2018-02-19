package model.gizmo;

import physics.Circle;
import physics.LineSegment;

import java.util.List;
import java.util.Set;

public class Square extends Gizmo {

    public Square(double x, double y) {
        this(x, y, generateID());
    }

    public Square(double x, double y, String id) {
        super(x, y, x, y, id);
    }

    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1, y1, x1 + 1, y1));
        lines.add(new LineSegment(x1, y1, x1, y1 + 1));
        lines.add(new LineSegment(x1 + 1, y1, x1 + 1, y1 + 1));
        lines.add(new LineSegment(x1, y1 + 1, x1 + 1, y1 + 1));

        circles.add(new Circle(x1, y1, 0));
        circles.add(new Circle(x1 + 1, y1, 0));
        circles.add(new Circle(x1 + 1, y1 + 1, 0));
        circles.add(new Circle(x1, y1 + 1, 0));
    }

    @Override
    public IEntity.Type getType() {
        return IEntity.Type.SQUARE;
    }

}
