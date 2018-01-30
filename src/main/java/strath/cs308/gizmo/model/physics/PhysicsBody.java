package strath.cs308.gizmo.model.physics;

import physics.Circle;
import physics.LineSegment;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;

import java.util.List;


public abstract class PhysicsBody implements IPhysicsBody
{
    private List<LineSegment> lineSegments;
    private List<Circle> circles;

    public abstract void onCollusion(IPhysicsBody collusion);
}
