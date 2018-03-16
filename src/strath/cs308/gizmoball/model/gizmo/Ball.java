package strath.cs308.gizmoball.model.gizmo;

import java.util.concurrent.CopyOnWriteArrayList;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.Vect;

import strath.cs308.gizmoball.model.IMovable;
import strath.cs308.gizmoball.model.gizmo.IGizmo;

public class Ball extends Gizmo implements IMovable {

    private Vect velocity;
    private double radius;
    private Circle circle;
    private boolean stopped;

    public Ball(double x, double y) {
        this(x, y, generateID());
    }

    public Ball(double x, double y, String id) {
        super(x - 0.25, y - 0.25, x + 0.25, y + 0.25, id);
    }


    @Override
    public void setVelocityRadian(double radian) {
        velocity = new Vect(new Angle(radian));
    }

    @Override
    public void setVelocity(double x, double y) {
        velocity = new Vect(x, y);
    }

    @Override
    public double getVelocityX() {
        return velocity.x();
    }

    @Override
    public double getVelocityY() {
        return velocity.y();
    }

    @Override
    public double getVelocityRadian() {
        return velocity.angle().radians();
    }

    public Circle getCircle() {
        return circle;
    }

    public double getX() {
        return circle.getCenter().x();
    }

    public void setX(double x) {
        circle = new Circle(x, getY(), radius);
        updateCircles();
    }

    public double getY() {
        return circle.getCenter().y();
    }

    public void setY(double y) {
        circle = new Circle(getX(), y, radius);
        updateCircles();
    }

    public boolean isStopped() {
        return stopped;
    }

    void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    private void updateCircles() {
        circles = new CopyOnWriteArrayList<>();
        circles.add(circle);

        x1 = circle.getCenter().x() - 0.25;
        y1 = circle.getCenter().y() - 0.25;
        x2 = circle.getCenter().x() + 0.25;
        y2 = circle.getCenter().y() + 0.25;
    }

    @Override
    public IGizmo.Type getType() {
        return IGizmo.Type.BALL;
    }

    @Override
    protected void setup(double x, double y, double x2, double y2) {
        radius = 0.25;
        circle = new Circle(x + 0.25, y + 0.25, radius);
        circles.add(circle);
        setVelocity(4, 4);
    }

    @Override
    public void move(double timeInSeconds) {
        setX(getX() + (getVelocityX() * timeInSeconds));
        setY(getY() + (getVelocityY() * timeInSeconds));
    }

    @Override
    public String toString() {
        String soFar = super.toString();
        soFar = soFar.substring(0, soFar.length() - 2);
        return soFar + " " + velocity.x() + " " + velocity.y() + "\n\n";
    }

    @Override
    public IMovable.Type getMovementType() {
        return IMovable.Type.LINEAR;
    }
}
