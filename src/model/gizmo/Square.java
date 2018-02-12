package model.gizmo;

import model.Circle;
import model.Line;

public class Square extends Gizmo {


    public Square(int x, int y, String id) {
        super(id);
        lines.add(new Line(x, y, x + 1, y));
        lines.add(new Line(x, y, x, y + 1));
        lines.add(new Line(x + 1, y, x + 1, y + 1));
        lines.add(new Line(x, y + 1, x + 1, y + 1));

        circles.add(new Circle(x, y, 0));
        circles.add(new Circle(x + 1, y, 0));
        circles.add(new Circle(x + 1, y+1, 0));
        circles.add(new Circle(x, y + 1, 0));
    }

    @Override
    public Type getType() {
        return Type.Square;
    }
}
