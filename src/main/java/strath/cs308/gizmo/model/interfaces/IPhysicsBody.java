package strath.cs308.gizmo.model.interfaces;

import physics.Circle;
import physics.LineSegment;

import java.util.List;

public interface IPhysicsBody
{
    List<Circle> getCircles();
    List<LineSegment> getLines();
}
