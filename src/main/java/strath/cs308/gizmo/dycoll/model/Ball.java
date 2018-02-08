package strath.cs308.gizmo.dycoll.model;

import mit.physics.Circle;
import mit.physics.Vect;

public class Ball extends PhysicalBody
{
    public Ball(double positionX, double positionY, double radius)
    {
        super();

        Vect midlePoint = new Vect(positionX, positionY);
        Circle circle = new Circle(midlePoint, radius);

        this.circles.add(circle);
    }

    @Override
    public void move(long time)
    {

    }
}
