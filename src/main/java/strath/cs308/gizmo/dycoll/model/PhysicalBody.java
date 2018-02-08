package strath.cs308.gizmo.dycoll.model;

import mit.physics.Angle;
import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

import java.util.ArrayList;
import java.util.List;

public abstract class PhysicalBody implements IPhysicalBody
{
    protected List<Circle> circles;
    protected List<LineSegment> lineSegments;
    protected Vect velocity;

    public PhysicalBody()
    {
        this.circles = new ArrayList<>();
        this.lineSegments = new ArrayList<>();
        this.velocity = new Vect(Angle.ZERO, 0.0);
    }

    public List<Circle> getCircles()
    {
        return circles;
    }

    public List<LineSegment> getLineSegments()
    {
        return lineSegments;
    }

    public Vect getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vect velocity)
    {
        this.velocity = velocity;
    }

    abstract public void move(double time);
}
