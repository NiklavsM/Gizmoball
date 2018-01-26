package strath.cs308.gizmo.model.physics;

import physics.Circle;
import physics.LineSegment;
import strath.cs308.gizmo.model.helper.FlipperDirection;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;
import strath.cs308.gizmo.model.interfaces.ITriggerable;

import java.util.List;

public class Flipper extends PhysicsBody
{
    private final FlipperDirection direction;

    public Flipper(FlipperDirection direction)
    {
        this.direction = direction;
    }

    @Override
    public void onCollusion(IPhysicsBody body)
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

    @Override
    public void onTrigger()
    {

    }

    @Override
    public void linkTrigger(ITriggerable other)
    {

    }
}
