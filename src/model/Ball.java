package model;


import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.*;

public class Ball extends Entity {

    private final double x;
    private final double y;
    private Vect velocity;
    private double radius;
    private Circle circle;
    private List<Circle> circles;
    private BitSet dots;

    public Ball(double x, double y,double xv, double yv) {
        this(x, y, xv, yv, generateID());
    }

    public Ball(double x, double y, double xv, double yv, String id) {
        super(id);
        this.x = x;
        this.y = y;

        circles = new LinkedList<>();

        velocity = new Vect(xv, yv);

        radius = 0.25;
        circle = new Circle(x, y, radius);
        circles.add(circle);
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


    public List<Dot> getDots() {
        List<Dot> dots = new LinkedList<>();
        circles.forEach(circle -> dots.add(new Dot(circle.getCenter().x(),
                circle.getCenter().y(),
                circle.getRadius())));
        return dots;
    }

}
