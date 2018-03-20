package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.model.IMovable;

public class Spinner extends Gizmo implements IMovable {

    private Vect velocity;
    private Circle spinAroundPoint;
    private Circle point1;
    private Circle point2;
    private Circle point3;
    private Circle point4;
    private LineSegment connector1;
    private LineSegment connector2;
    private LineSegment connector3;
    private LineSegment connector4;

    public Spinner(double x1, double y1) {
        this(x1, y1, generateID());
    }

    public Spinner(double x1, double y1, String id) {
        super(x1, y1, x1 + 2, y1 + 2, id);

        velocity = new Vect( Angle.DEG_180);
    }

    @Override
    public void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);

        double radius = 0.25;
        connector1 = new LineSegment(x1 + radius, y1 + 0.75, x2 - radius, y1 + 0.75);
        connector2 = new LineSegment(x1 + radius, y1 + 1.25, x2 - radius, y1 + 1.25);
        connector3 = new LineSegment(x1 + 0.75, y1 + radius, x1 + 0.75, y2 - radius);
        connector4 = new LineSegment(x1 + 1.25, y1 + radius, x1 + 1.25, y2 - radius);

        lines.add(connector1);
        lines.add(connector2);
        lines.add(connector3);
        lines.add(connector4);

        point1 = new Circle(x1 + radius, y1 + 1, radius);
        point2 = new Circle(x2 - radius, y1 + 1, radius);
        point3 = new Circle(x1 + 1, y1 + radius, radius);
        point4 = new Circle(x1 + 1, y2 - radius, radius);

        circles.add(point1);
        circles.add(point2);
        circles.add(point3);
        circles.add(point4);

        spinAroundPoint = new Circle(x1 + 1, y1 + 1, 0);

        setColor("#B388FF");

    }

    @Override
    public boolean isMoving() {
        return velocity == Vect.ZERO;
    }

    @Override
    public void move(double time) {


        double rotationRadian = velocity.angle().radians() * time;
        Angle rotationAngle = new Angle(rotationRadian);

        circles.clear();
        point1 = spinCircle(point1, rotationAngle);
        point2 = spinCircle(point2, rotationAngle);
        point3 = spinCircle(point3, rotationAngle);
        point4 = spinCircle(point4, rotationAngle);
        lines.clear();
        connector1 = spinLine(connector1, rotationAngle);
        connector2 = spinLine(connector2, rotationAngle);
        connector3 = spinLine(connector3, rotationAngle);
        connector4 = spinLine(connector4, rotationAngle);
    }

    private LineSegment spinLine(LineSegment line, Angle rotationAngle) {
        LineSegment rotatedLine = Geometry.rotateAround(line
                , spinAroundPoint.getCenter()
                , rotationAngle);
        lines.add(rotatedLine);
        return rotatedLine;
    }

    private Circle spinCircle(Circle circleToSpin, Angle rotationAngle) {
        Circle rotated = Geometry.rotateAround(circleToSpin
                , spinAroundPoint.getCenter()
                , rotationAngle);

        circles.add(rotated);
        return rotated;
    }

    public Vect getCurrentVelocity() {
        return velocity;
    }

    @Override
    public Circle getSpinAround() {
        return spinAroundPoint;
    }

    @Override
    public void setVelocityRadian(double radian) {

    }

    @Override
    public void setVelocity(double x, double y) {

    }

    @Override
    public double getVelocityX() {
        return 0;
    }

    @Override
    public double getVelocityY() {
        return 0;
    }

    @Override
    public double getVelocityRadian() {
        return 0;
    }

    @Override
    public IMovable.Type getMovementType() {
        return null;
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.SPINNER;
    }
}
