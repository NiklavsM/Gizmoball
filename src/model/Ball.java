package model;


import model.gizmo.Gizmo;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Ball extends Gizmo {

    private Vect velocity;
    private double radius;
    private Circle circle;

    public Ball(double x, double y,double xv, double yv) {
        this(x, y, xv, yv, generateID());
    }

    public Ball(double x, double y, double xv, double yv, String id) {
        super(x, y, x, y, id);
        velocity = new Vect(xv, yv);

    }

    Vect getVelo() {
        return velocity;
    }

    void setVelo(Vect v) {
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
    public List<Circle> getCircles() {
        circles = new LinkedList<>();
        circles.add(circle);
        return circles;
    }


    @Override
    public Type getType() {
        return Type.Ball;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x, double y, double x2, double y2) {
        radius = 0.25;
        circle = new Circle(x, y, radius);
        circles.add(circle);
    }

}
