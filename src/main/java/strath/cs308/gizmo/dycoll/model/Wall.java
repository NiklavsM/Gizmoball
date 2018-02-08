package strath.cs308.gizmo.dycoll.model;

import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

public class Wall extends PhysicalBody
{
    public Wall(double startX, double startY, double endX, double endY)
    {
        Vect startPoint = new Vect(startX, startY);
        Vect endPoint = new Vect(endX, endY);

        LineSegment lineSegment = new LineSegment(startPoint, endPoint);

        this.lineSegments.add(lineSegment);

        Circle closingCircle1 = new Circle(startPoint, 0);
        Circle closingCircle2 = new Circle(endPoint, 0);

        this.circles.add(closingCircle1);
        this.circles.add(closingCircle2);
    }

    @Override
    public void move(double time)
    {

    }
}
