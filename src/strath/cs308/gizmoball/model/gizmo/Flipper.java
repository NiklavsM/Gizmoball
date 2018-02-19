package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.ITriggerable;

import java.util.List;
import java.util.Set;

public class Flipper extends Gizmo implements IMovable, ITriggerable {

    enum Movement {
        BACK, FORWARD, BOTTOM, TOP;
    }

    public enum Orientation {
        LEFT(1)
        , RIGHT(-1);

        private double mult;

        Orientation(double mult) {
                               this.mult = mult;
                                                }

        public double getMult() {
            return mult;
        }
    }


    private Circle startPoint;
    private Circle endPoint;
    private LineSegment connector1;
    private LineSegment connector2;

    private Vect velocity;
    private double movedAngle;
    private Movement movementStatus;
    private Orientation orientation;
    public Flipper(double x, double y, Orientation orientation){
                                                              this(x,y,orientation,generateID());
                                                                                                 }
    public Flipper(double x, double y, Orientation orientation, String id) {
        super(x,y,x,y,id); // Needs to be fixed ASP WHYYY all gizmos need to have 4 cordinates Nik

        double radius = 0.25;
        this.orientation = orientation;

        movementStatus = Movement.BOTTOM;
        movedAngle = Angle.ZERO.radians();

//        velocity = new Vect(Angle.DEG_180);
        velocity = Vect.ZERO;

        if (orientation == Orientation.RIGHT) {
            x = (x + 2) - radius ;
        } else {
            x = x + radius;
        }

        startPoint = new Circle(x, y + radius, radius);
        endPoint = new Circle(x, y + radius + 1.5, radius);

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

    public void rotate(Angle angle) {

    }

    @Override
    public Type getType() {
                        return Gizmo.Type.FLIPPER;
                                                  }

    @Override
    public void move(double time) {
        if (!velocity.equals(Vect.ZERO)) {
            double rotationRadian = velocity.angle().radians() * time;

            if ((rotationRadian + movedAngle) > Angle.DEG_90.radians()) {
                rotationRadian = Angle.DEG_90.radians() - movedAngle;
            }

            Angle rotationAngle = new Angle(rotationRadian);


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
            movedAngle += Math.abs(rotationRadian) ;

            if (movedAngle >= Angle.DEG_90.radians()) {


                if (movementStatus.equals(Movement.FORWARD)) {
                    movementStatus = Movement.TOP;
                    velocity = Vect.ZERO;
                    movedAngle = Angle.ZERO.radians();
                }

                if (movementStatus.equals(Movement.BACK)) {
                    movementStatus = Movement.BOTTOM;
                    velocity = Vect.ZERO;
                    movedAngle = Angle.ZERO.radians();
                }

            }
        }

    }

    public Vect getVelocity() {
                            return velocity;
                                            }

    public void setVelocity(Vect velocity) {
        this.velocity = new Vect(new Angle(velocity.angle().radians() * orientation.getMult()));
    }

    private void up() {
        if (movementStatus == Movement.BACK && movedAngle != 0) {
            movementStatus = Movement.FORWARD;
            movedAngle = Angle.DEG_90.radians() - movedAngle ;
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * -1 * orientation.getMult()));
        }

        if (movementStatus.equals(Movement.BOTTOM)) {
            movedAngle = Angle.ZERO.radians();
            movementStatus = Movement.FORWARD;
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * -1 * orientation.getMult()));
        }

    }

    private void down() {

        if (movementStatus.equals(Movement.TOP)) {
            movementStatus = Movement.BACK;
            movedAngle = Angle.ZERO.radians();
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * orientation.getMult()));
        }

        if (movementStatus == Movement.FORWARD && movedAngle != 0) {
            movementStatus = Movement.BACK;
            movedAngle = Angle.DEG_90.radians() - movedAngle ;
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * orientation.getMult()));
        }

    }

    public Circle getStartPoint() {
        return startPoint;
    }

    @Override
    public void trigger(String triggerEvent) {
        triggerEvent = triggerEvent.toUpperCase();
        String letter = (orientation.equals(Orientation.RIGHT))? "L" : "K";
        if (triggerEvent.equals("KEY_PRESSED_"+letter)) {
            up();
        }

        if (triggerEvent.equals("KEY_RELEASED_"+letter)){
           down();
        }
    }


        @Override
    public void draw(Set<LineSegment> lines, List<Circle> circles, double x1, double y1, double x2, double y2) {

    }

}
