package model;


public class Absorber extends Gizmo {
    public Absorber(double x1, double y1, double x2, double y2, String id) {
        super(id);
        lines.add(new Line(x1,y1,x2,y1));
        lines.add(new Line(x1,y1,x1,y2));
        lines.add(new Line(x1,y2,x2,y2));
        lines.add(new Line(x2,y1,x2,y2));
        circles.add(new Circle(x1,y1,0));
        circles.add(new Circle(x1,y2,0));
        circles.add(new Circle(x2,y1,0));
        circles.add(new Circle(x2,y2,0));
    }


    @Override
    public Type getType() {
        return Type.Absorber;
    }

}
