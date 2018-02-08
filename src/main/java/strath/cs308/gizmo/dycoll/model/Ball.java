package strath.cs308.gizmo.dycoll.model;

import mit.physics.Circle;
import mit.physics.Vect;

public class Ball extends PhysicalBody
{
    private Circle circle;

    public Ball(double positionX, double positionY, double radius)
    {
        super();

        Vect midlePoint = new Vect(positionX, positionY);
        this.circle = new Circle(midlePoint, radius);

        this.circles.add(this.circle);
    }

    @Override
    public void move(double time)
    {
        Vect newMidlePoint = new Vect(this.circle.getCenter().x() + (this.getVelocity().x() * time),
                this.circle.getCenter().y() + (this.getVelocity().y() * time));

        Circle temp = new Circle(newMidlePoint, this.circle.getRadius());

        System.out.println(temp.getCenter().x() + " / " + temp.getCenter().y() );

        this.circle = temp;

        this.getCircles().clear();
        this.getCircles().add(circle);
    }
}
