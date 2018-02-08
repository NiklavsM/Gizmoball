package model;

import physics.Circle;
import physics.Vect;

public class Ball extends Gizmo implements IMovable {

    private Circle circle;
    private Vect velocity;

    public Ball(double centerX, double centerY, double radius, String name) {
        super(name);
        circle = new Circle(centerX, centerY, radius);
        circles.add(this.circle);
        velocity = new Vect(0, 20);
    }

    public Ball(double centerX, double centerY, String name) {
        this(centerX, centerY, 0.5, name);
    }

    public Ball(double centerX, double centerY) {
        this(centerX, centerY, "Undefined"); //FIXME
    }

    public Ball(double centerX, double centerY, double radius) {
        this(centerX, centerY, radius, "Undefined"); // FIXME
    }

    @Override
    public void move(double time) {
        Vect newMidlePoint = new Vect(this.circle.getCenter().x() + (this.getVelocity().x() * time),
                this.circle.getCenter().y() + (this.getVelocity().y() * time));

        Circle temp = new Circle(newMidlePoint, this.circle.getRadius());

        circle = temp;

        getCircles().clear();
        getCircles().add(circle);
    }

    @Override
    public Vect getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vect velocity) {
        this.velocity = velocity;
    }

    @Override
    public void rotate(int degrees) {

    }

    @Override
    public Type getType() {
        return Type.Ball;
    }

}
