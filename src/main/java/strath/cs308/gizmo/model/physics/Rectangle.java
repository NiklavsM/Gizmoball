package strath.cs308.gizmo.model.physics;

import physics.Circle;
import physics.LineSegment;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;
import strath.cs308.gizmo.model.interfaces.ITrigger;
import strath.cs308.gizmo.model.interfaces.ITriggerable;

import java.util.List;

public class Rectangle extends PhysicsBody implements ITrigger, ITriggerable
{
    @Override
    public void onCollision(IPhysicsBody body)
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
    public void trigger()
    {

    }

    @Override
    public boolean addTriggerTarget(ITriggerable target)
    {
        return false;
    }


    @Override
    public void onTrigger()
    {

    }
}
