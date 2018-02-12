package model.gizmo;


import physics.Circle;
import physics.LineSegment;

public class Absorber extends Gizmo {

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(id);
        lines.add(new LineSegment(x1,y1,x2,y1));
        lines.add(new LineSegment(x1,y1,x1,y2));
        lines.add(new LineSegment(x1,y2,x2,y2));
        lines.add(new LineSegment(x2,y1,x2,y2));
        circles.add(new Circle(x1,y1,0));
        circles.add(new Circle(x2,y1,0));
        circles.add(new Circle(x2,y2,0));
        circles.add(new Circle(x1,y2,0));
    }

    public Absorber (double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, generateID());
    }

    @Override
    public Type getType() {
        return Type.Absorber;
    }

}
