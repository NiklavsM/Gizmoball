package strath.cs308.gizmo.dycoll.model;

import mit.physics.Circle;
import mit.physics.LineSegment;
import mit.physics.Vect;

import java.util.List;

public interface IPhysicalBody
{
    void setVelocity(Vect velocity);
    Vect getVelocity();

    List<LineSegment> getLineSegments();
    List<Circle> getCircles();

    void move(double time);
}
