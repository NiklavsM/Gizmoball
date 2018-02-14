package model;

import mit.physics.*;

public class Flipper extends Gizmo implements IMovable {

    enum Movement {
        BACK, FORWARD, STILL;
    }

    private Circle startPoint;
    private Circle endPoint;
    private LineSegment connector1;
    private LineSegment connector2;

    private Vect velocity;
    private boolean isMoving;
    private double movedAngle;
    private Movement movementStatus;

    public Flipper(String name, double startX, double startY) {
        super(name);

        double radius = 0.25;

        movementStatus = Movement.FORWARD;
        movedAngle = Angle.ZERO.radians();

        velocity = new Vect(Angle.DEG_90);


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
        double rotationRadian = velocity.angle().radians() * time;
        if (movementStatus == Movement.FORWARD) {
            rotationRadian *= -1;
        }
        Angle rotationAngle = new Angle(rotationRadian);
        movedAngle += Math.abs(rotationRadian) ;

        if (movedAngle > Angle.DEG_90.radians()) {

            switch (movementStatus) {
                case FORWARD:
                    movementStatus = Movement.BACK;
                break;

                case BACK:
                    movementStatus = Movement.STILL;
                break;
            }

            movedAngle = Angle.ZERO.radians();
        }

        if (movementStatus != Movement.STILL) {



            Circle rotated = Geometry.rotateAround(endPoint
                    , startPoint.getCenter()
                    , rotationAngle);


            circles.remove(endPoint);
            endPoint = rotated;
            circles.add(endPoint);
            lines.remove(connector1);
            lines.remove(connector2);

            connector1 = Geometry.rotateAround(connector1
                    , startPoint.getCenter()
                    , rotationAngle);
            connector2 = Geometry.rotateAround(connector2
                    , startPoint.getCenter()
                    , rotationAngle);

            lines.add(connector1);
            lines.add(connector2);
        }
    }

    @Override
    public Vect getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }

    public Circle getStartPoint()
    {
        return startPoint;
    }
}
