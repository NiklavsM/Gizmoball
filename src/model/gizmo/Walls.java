package model.gizmo;

import model.Line;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Walls extends Gizmo {

    // Walls are the enclosing Rectangle - defined by top left corner and bottom
    // right
    public Walls(double x1, double y1, double x2, double y2, String id) {
        super(id);
        lines.add(new Line(x1, y1, x1, y2));
        lines.add(new Line(x1, y1, x2, y1));
        lines.add(new Line(x2, y1, x2, y2));
        lines.add(new Line(x1, y2, x2, y2));
    }

    @Override
    public Type getType() {
        return Type.Wall;
    }
}
