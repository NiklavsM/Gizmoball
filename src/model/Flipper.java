package model;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

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
    public void move(double time)
    {

    }

    @Override
    public Vect getVelocity()
    {
        return velocity;
    }

    @Override
    public void setVelocity(Vect velocity)
    {
        this.velocity = velocity;
    }
}
