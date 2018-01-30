package strath.cs308.gizmo.model.physics;

import physics.Circle;
import physics.LineSegment;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;

import java.util.List;

public class Absorber extends PhysicsBody
{
    @Override
    public void onCollusion(IPhysicsBody collusion)
    {

    }

    @Override
    public List<Circle> getCircles()
    {
        return null;
    }

    @Override
    public List<LineSegment> getLines()
    {
        return null;
    }
}
