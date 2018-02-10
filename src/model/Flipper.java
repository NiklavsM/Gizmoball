package model;

import mit.physics.*;

public class Flipper extends Gizmo implements IMovable {

    private Circle startPoint;
    private Circle endPoint;
    private LineSegment connector1;
    private LineSegment connector2;

    private Vect velocity;

    public Flipper(String name, double startX, double startY) {
        super(name);

        double radius = 0.25;

        velocity = Vect.ZERO;

        startPoint = new Circle(startX + radius, startY + radius, radius);
        endPoint = new Circle(startX + radius, startY + radius + 1.5, radius);

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
    public void rotate(Angle angle) {

    }

    @Override
    public Type getType() {
        return Gizmo.Type.Flipper;
    }

    @Override
    public void move(double time) {
        Circle rotated = Geometry.rotateAround(endPoint, new Vect(startPoint.getCenter().x(), startPoint.getCenter().y()), new Angle(-1.57079633 * time));


        circles.remove(endPoint);
        endPoint = rotated;
        circles.add(endPoint);
        lines.remove(connector1);
        lines.remove(connector2);

        connector1 = Geometry.rotateAround(connector1, new Vect(startPoint.getCenter().x(), startPoint.getCenter().y()), new Angle(-1.57079633 * time));
        connector2 = Geometry.rotateAround(connector2, new Vect(startPoint.getCenter().x(), startPoint.getCenter().y()), new Angle(-1.57079633 * time));

        lines.add(connector1);
        lines.add(connector2);
    }

    @Override
    public Vect getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }
}
