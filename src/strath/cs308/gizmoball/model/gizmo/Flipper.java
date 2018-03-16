package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.triggeringsystem.DefaultTriggarable;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.Iterator;
import java.util.List;


public class Flipper extends Gizmo implements IMovable, IAction, ITriggerable {

    private final DefaultTriggarable defaultTriggarable;
    private Circle startPoint;
    private Circle endPoint;
    private LineSegment connector1;
    private LineSegment connector2;
    private Vect velocity;
    private Vect velocityConstant;
    private double movedAngle;
    private Movement movementStatus;
    private Orientation orientation;
    private boolean isCycle;

    public Flipper(double x, double y, Orientation orientation) {
        this(x, y, orientation, generateID());
    }

    public Flipper(double x, double y, Orientation orientation, String id) {
        super(x, y, x + 2, y + 2, id);

        double radius = 0.25;
        this.orientation = orientation;

        movementStatus = Movement.BOTTOM;
        movedAngle = Angle.ZERO.radians();

        velocityConstant = new Vect(Angle.DEG_180);
        velocity = Vect.ZERO;
        isCycle = false;

        if (orientation == Orientation.RIGHT) {
            x = (x + 2) - radius;
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

        defaultTriggarable = new DefaultTriggarable();
        setAction(this);
    }

    @Override
    public IGizmo.Type getType() {
        if (orientation.equals(Orientation.RIGHT)) {
            return IGizmo.Type.RIGHT_FLIPPER;
        }

        if (orientation.equals(Orientation.LEFT)) {
            return IGizmo.Type.LEFT_FLIPPER;
        }

        return IGizmo.Type.FLIPPER;
    }

    @Override
    protected void setup(double x1, double y1, double x2, double y2) {

    }

    @Override
    public void move(double time) {
        if (!velocity.equals(Vect.ZERO)) {
            double rotationRadian = velocity.angle().radians() * time;

            if ((Math.abs(rotationRadian) + movedAngle) > Angle.DEG_90.radians()) {
                rotationRadian = (Angle.DEG_90.radians() - movedAngle) * orientation.getMult();
                if (movementStatus.equals(Movement.FORWARD)) {
                    rotationRadian *= -1;
                }
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
            movedAngle += Math.abs(rotationRadian);

            if (movedAngle >= Angle.DEG_90.radians()) {


                if (movementStatus.equals(Movement.FORWARD)) {
                    movementStatus = Movement.TOP;
                    velocity = Vect.ZERO;
                    movedAngle = Angle.ZERO.radians();
                    if (isCycle) {
                        isCycle = false;
                        down();
                        return;
                    }
                }

                if (movementStatus.equals(Movement.BACKWARDS)) {
                    movementStatus = Movement.BOTTOM;
                    velocity = Vect.ZERO;
                    movedAngle = Angle.ZERO.radians();
                }

            }
        }

    }

    public Vect getVelocity() {
        return velocityConstant;
    }

    public void setVelocity(Vect velocity) {
        velocityConstant = velocity;
    }

    @Override
    public void setVelocityRadian(double radian) {
        velocityConstant = new Vect(new Angle(radian));
    }

    @Override
    public void setVelocity(double x, double y) {
        velocityConstant = new Vect(x, y);
    }

    @Override
    public double getVelocityX() {
        return velocityConstant.x();
    }

    @Override
    public double getVelocityY() {
        return velocityConstant.y();
    }

    @Override
    public double getVelocityRadian() {
        return velocityConstant.angle().radians();
    }

    private void up() {
        if (movementStatus == Movement.BACKWARDS) {
            movementStatus = Movement.FORWARD;
            movedAngle = Angle.DEG_90.radians() - movedAngle;
            velocity = new Vect(new Angle(velocityConstant.angle().radians() * -1 * orientation.getMult()));
            return;
        }

        if (movementStatus.equals(Movement.BOTTOM)) {
            movedAngle = Angle.ZERO.radians();
            movementStatus = Movement.FORWARD;
            velocity = new Vect(new Angle(velocityConstant.angle().radians() * -1 * orientation.getMult()));
        }

    }

    private void down() {

        if (movementStatus.equals(Movement.TOP)) {
            movementStatus = Movement.BACKWARDS;
            movedAngle = Angle.ZERO.radians();
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * orientation.getMult()));
            return;
        }

        if (movementStatus == Movement.FORWARD) {
            movementStatus = Movement.BACKWARDS;
            movedAngle = Angle.DEG_90.radians() - movedAngle;
            velocity = new Vect(new Angle(Angle.DEG_180.radians() * orientation.getMult()));
        }

    }

    public Circle getStartPoint() {
        return startPoint;
    }

    @Override
    public void doAction(Object args) {
        if (args == null) {
            return;
        }

        if (args.equals("TRIGGER")) {
            //isCycle = true;
            if (movementStatus.equals(Movement.TOP)) {
                down();
            } else {
                up();
            }
            return;
        }

        if (args instanceof Movement) {
            Movement upOrDown = (Movement) args;
            if (upOrDown.equals(Movement.FORWARD)) {
                up();
                return;
            }
            if (upOrDown.equals(Movement.BACKWARDS)) {
                down();
                return;
            }
        }

    }

    @Override
    public void rotate() {
        super.rotate();

        startPoint = circles.get(0);
        endPoint = circles.get(1);
        Iterator<LineSegment> lineIt = lines.iterator();
        connector1 = lineIt.next();
        connector2 = lineIt.next();
    }

    @Override
    public void performAction(Object args) {
        defaultTriggarable.performAction(args);
    }

    @Override
    public void setAction(IAction triggerAction) {
        defaultTriggarable.setAction(triggerAction);
    }

    @Override
    public IAction getCurrentAction() {
        return defaultTriggarable.getCurrentAction();
    }

    @Override
    public List<IAction> getAvailableActions() {
        return getAvailableActions();
    }

    @Override
    public boolean addAvailableAction(IAction action) {
        return defaultTriggarable.addAvailableAction(action);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public IMovable.Type getMovementType() {
        return IMovable.Type.ROTATION;
    }

    public enum Movement {
        BACKWARDS, FORWARD, BOTTOM, TOP
    }

    public enum Orientation {
        LEFT(1), RIGHT(-1);

        private double mult;

        Orientation(double mult) {
            this.mult = mult;
        }

        public double getMult() {
            return mult;
        }
    }
}
