package strath.cs308.gizmoball.model.gizmo;

import mit.physics.*;
import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.triggeringsystem.DefaultTriggarable;
import strath.cs308.gizmoball.model.triggeringsystem.IAction;
import strath.cs308.gizmoball.model.triggeringsystem.ITriggerable;

import java.util.Set;

public class Spinner extends Gizmo implements IMovable, ITriggerable, IAction {

    private final DefaultTriggarable triggerable = new DefaultTriggarable();

    private double velocity;
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

        velocity = 3.14 * -1;
        setReflectionCoefficient(0.9);


        setAction(this);
        addActionTrigger("collision");
    }

    @Override
    public void setup(double x1, double y1, double x2, double y2) {
        super.setup(x1, y1, x2, y2);

        double radius = 0.25;
        connector1 = new LineSegment(x1 + radius*2, y1 + 0.75, x2 - radius*2, y1 + 0.75);
        connector2 = new LineSegment(x1 + radius*2, y1 + 1.25, x2 - radius*2, y1 + 1.25);
        connector3 = new LineSegment(x1 + 0.75, y1 + radius*2, x1 + 0.75, y2 - radius*2);
        connector4 = new LineSegment(x1 + 1.25, y1 + radius*2, x1 + 1.25, y2 - radius*2);

        lines.add(connector1);
        lines.add(connector2);
        lines.add(connector3);
        lines.add(connector4);

        point1 = new Circle(x1 + radius*2, y1 + 1, radius);
        point2 = new Circle(x2 - radius*2, y1 + 1, radius);
        point3 = new Circle(x1 + 1, y1 + radius*2, radius);
        point4 = new Circle(x1 + 1, y2 - radius*2, radius);

        circles.add(point1);
        circles.add(point2);
        circles.add(point3);
        circles.add(point4);

        spinAroundPoint = new Circle(x1 + 1, y1 + 1, 0);

        setColor("#B388FF");

    }

    @Override
    public boolean isMoving() {
        return velocity > 0;
    }

    @Override
    public void move(double time) {


        double rotationRadian = velocity * time;
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

    public Double getCurrentRadianVelocity() {
        return velocity;
    }

    @Override
    public double getSpinAroundX() {
        return spinAroundPoint.getCenter().x();
    }

    @Override
    public double getSpinAroundY() {
        return spinAroundPoint.getCenter().y();
    }

    @Override
    public void setVelocityRadian(double radian) {
        velocity = radian;
    }

    @Override
    public void setVelocity(double x, double y) {
        Vect temp = new Vect(x, y);
        velocity = temp.angle().radians();
    }

    @Override
    public double getVelocityX() {
        Vect temp = new Vect(new Angle(velocity));
        return temp.x();
    }

    @Override
    public double getVelocityY() {
        Vect temp = new Vect(new Angle(velocity));
        return temp.y();
    }

    @Override
    public double getVelocityRadian() {
        return velocity;
    }

    @Override
    public IMovable.Type getMovementType() {
        return IMovable.Type.ROTATION;
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.SPINNER;
    }

    @Override
    public void performAction(Object args) {
        triggerable.performAction(args);
    }

    @Override
    public void setAction(IAction triggerAction) {
        triggerable.setAction(triggerAction);
    }

    @Override
    public IAction getCurrentAction() {
        return triggerable.getCurrentAction();
    }

    @Override
    public Set<String> getAvailableActions() {
        return triggerable.getAvailableActions();
    }

    @Override
    public boolean addActionTrigger(String trigger) {
        return triggerable.addActionTrigger(trigger);
    }

    @Override
    public boolean removeActionTrigger(String trigger) {
        return triggerable.removeActionTrigger(trigger);
    }

    @Override
    public Set<String> getTriggers() {
        return triggerable.getTriggers();
    }

    @Override
    public String id() {
        return getId();
    }

    @Override
    public void doAction(Object args)
    {
        velocity *= -1;
    }

    @Override
    public boolean addAvailableAction(String actionName, IAction action) {
        return triggerable.addAvailableAction(actionName, action);
    }

    @Override
    public boolean setAction(String actionName) {
        return triggerable.setAction(actionName);
    }

    @Override
    public String getCurrentActionName() {
        return triggerable.getCurrentActionName();
    }
}
