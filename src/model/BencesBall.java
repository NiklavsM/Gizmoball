package model;

import physics.Circle;
import physics.Vect;

public class BencesBall extends Gizmo implements IMovable {
    private Circle circle;
    private Vect velocity;

    public BencesBall(String name, double centerX, double centerY, double radius) {
        super(name);
        circle = new Circle(centerX, centerY, radius);
        circles.add(this.circle);
        velocity = new Vect(0, 20);
    }

    public BencesBall(double centerX, double centerY, double radius) {
        this("undefined", centerX, centerY, radius);
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
