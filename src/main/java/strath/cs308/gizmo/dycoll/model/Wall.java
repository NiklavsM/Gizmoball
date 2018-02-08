package strath.cs308.gizmo.dycoll.model;

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
    }

    @Override
    public void move(double time)
    {

    }
}
