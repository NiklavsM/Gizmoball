package model;

public class Triangle extends Gizmo {

    public Triangle(double x, double y, String id) {
        super(id);
        lines.add(new Line(x, y, x + 1, y));
        lines.add(new Line(x + 1, y, x + 1, y + 1));
        lines.add(new Line(x, y, x + 1, y + 1));
        circles.add(new Circle(x,y,0));
        circles.add(new Circle(x+1,y,0));
        circles.add(new Circle(x+1,y+1,0));
    }

    @Override
    public Type getType() {
        return Type.Triangle;
    }
}
