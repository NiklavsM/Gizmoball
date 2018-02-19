package model.gizmo;

import physics.Circle;
import physics.LineSegment;

import java.util.List;
import java.util.Set;

public class Absorber extends Gizmo {

    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(x1, y1, x2, y2, id);
    }

    public Absorber (double x1, double y1, double x2, double y2) {
        this(x1, y1, x2, y2, generateID());
    }

    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {
        lines.add(new LineSegment(x1,y1,x2,y1));
        lines.add(new LineSegment(x2,y1,x2,y2));
        lines.add(new LineSegment(x2,y2,x1,y2));
        lines.add(new LineSegment(x1,y2,x1,y1));
        circles.add(new Circle(x1,y1,0));
        circles.add(new Circle(x2,y1,0));
        circles.add(new Circle(x2,y2,0));
        circles.add(new Circle(x1,y2,0));
    }

    @Override
    public IEntity.Type getType() {
        return IEntity.Type.Absorber;
    }

}
