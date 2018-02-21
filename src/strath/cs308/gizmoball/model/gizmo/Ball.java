package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.IMovable;

import java.util.*;

public class Ball extends Gizmo implements IMovable {

    private Vect velocity;
    private double radius;
    private Circle circle;

    public Ball(double x, double y) {
        this(x, y, generateID());
    }

    public Ball(double x, double y, String id) {
        super(x, y, x, y, id);
    }

    public Vect getVelocity() {
        return velocity;
    }

    public void setVelocity(Vect v) {
        velocity = v;
    }
    
    double getRadius() {
    	return radius;
    }

    public Circle getCircle() {
        return circle;
    }

    double getExactX() {
        return circle.getCenter().x();
    }

    double getExactY() {
        return circle.getCenter().y();
    }

    void setExactX(double x) {
        circle = new Circle(x, getExactY(), radius);
    }

    void setExactY(double y) {
        circle = new Circle(getExactX(), y, radius);
    }

    @Override
    public Set<LineSegment> getLines() {
        return new HashSet<>();
    }

    public List<Circle> getCircles() {
        circles = new LinkedList<>();
        circles.add(circle);
        return circles;
     }

    @Override
    public Type getType() {
        return Type.BALL;
    }

    @Override
    protected void setup(double x, double y, double x2, double y2) {
        radius = 0.25;
        circle = new Circle(x, y, radius);
        circles.add(circle);
        setVelocity(new Vect(4,4));
    }

    @Override
    public void move(double timeInSeconds) {
        setExactX(getExactX() + (getVelocity().x() * timeInSeconds));
        setExactY(getExactY() + (getVelocity().y() * timeInSeconds));

    }
}
