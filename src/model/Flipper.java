package model;

import model.gizmo.Gizmo;
import physics.Angle;
import physics.LineSegment;
import physics.*;

import java.util.List;
import java.util.Set;

public class Flipper extends Gizmo implements IMovable {

    private Circle startPoint;
    private Circle endPoint;
    private LineSegment connector1;
    private LineSegment connector2;

    private Vect velocity;

    //FORTESTING
    private double moveCooldown;

    public Flipper(double x, double y) {
        this(x, y, generateID());
    }

    public Flipper(double x, double y, String id) {
        super(x, y, x, y,  .id);

        double radius = 0.25;
        rotatingPoint = new Vect(x + 1, y + 1);
        moveCooldown = 2;

        velocity = new Vect(new Angle(-1.57079633));

        startPoint = new Circle(x + radius, y + radius, radius);
        endPoint = new Circle(x + radius, y + radius + 1.5, radius);

        connector1 = new LineSegment(startPoint.getCenter().x() - radius
                , startPoint.getCenter().y()
                , endPoint.getCenter().x() - radius
                , endPoint.getCenter().y());


        connector2 = new LineSegment(startPoint.getCenter().x() + radius
                , startPoint.getCenter().y()
                , endPoint.getCenter().x() + radius
                , endPoint.getCenter().y());

        lines.add(connector1);
        lines.add(connector2);
        circles.add(startPoint);
        circles.add(endPoint);
    }

    @Override
    public Type getType() {
        return Gizmo.Type.Flipper;
    }

    @Override
    public void move(double time) {
        Circle rotated = Geometry.rotateAround(endPoint
                , startPoint.getCenter()
                , new Angle(velocity.angle().radians() * time));


        circles.remove(endPoint);
        endPoint = rotated;
        circles.add(endPoint);
        lines.remove(connector1);
        lines.remove(connector2);

        connector1 = Geometry.rotateAround(connector1
                , startPoint.getCenter()
                , new Angle(velocity.angle().radians() * time));
        connector2 = Geometry.rotateAround(connector2
                , startPoint.getCenter()
                , new Angle(-1.57079633 * time));

        lines.add(connector1);
        lines.add(connector2);
    }

    @Override
    public List<Circle> getCircles(){
        return circles;
    }

    @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {

    }


    public Vect getVelocity() {
        return velocity;
    }


    public Circle getStartPoint() {
        return startPoint;
    }


}
