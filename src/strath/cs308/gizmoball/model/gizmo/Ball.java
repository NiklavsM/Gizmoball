package strath.cs308.gizmoball.model.gizmo;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.Vect;
import strath.cs308.gizmoball.model.IMovable;

import java.util.concurrent.CopyOnWriteArrayList;

public class Ball extends Gizmo implements IMovable {

    private Vect velocity;
    private double radius;
    private Circle circle;
    private boolean isMoving = true;

    public Ball(double x, double y) {
        this(x, y, generateID());
    }

    public Ball(double x, double y, String id) {
        super(x - 0.25, y - 0.25, x + 0.25, y + 0.25, id);
        setVelocity(4, 4);
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

    @Override
    public void setVelocityRadian(double radian) {
        velocity = new Vect(new Angle(radian));
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

    public double getRadius() {
        return (getEndX() - getStartX()) / 2;
    }

    public boolean isMoving() {
        return isMoving;
    }

    @Override
    public Double getCurrentRadianVelocity() {
        return velocity.angle().radians();
    }

    @Override
    public double getSpinAroundX() {
        return circle.getCenter().x();
    }

    @Override
    public double getSpinAroundY() {
        return circle.getCenter().y();
    }

    void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
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
        super.setup(x, y, x2, y2);
        radius = 0.25;
        circle = new Circle(x + 0.25, y + 0.25, radius);
        circles.add(circle);
    }

    @Override
    public boolean overlapsWithGizmo(IGizmo g) {
        boolean r = super.overlapsWithGizmo(g);
        if (r && g.getType().equals(IGizmo.Type.ABSORBER)) {
            return false;
        }
        return r;
    }

    @Override
    public void move(double timeInSeconds) {
        setX(getX() + (getVelocityX() * timeInSeconds));
        setY(getY() + (getVelocityY() * timeInSeconds));
    }

    @Override
    public String toString() {
        return getType() + " " + id
                + " " + getCircle().getCenter().x() + " "
                + getCircle().getCenter().y()
                + " " + velocity.x() + " " + velocity.y() + "\n";
    }

    @Override
    public IMovable.Type getMovementType() {
        return IMovable.Type.LINEAR;
    }
}
