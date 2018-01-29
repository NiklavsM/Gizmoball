package strath.cs308.gizmo.model.physics;

import physics.LineSegment;
import strath.cs308.gizmo.model.interfaces.IPhysicsBody;
import strath.cs308.gizmo.model.interfaces.ITrigger;
import strath.cs308.gizmo.model.interfaces.ITriggerable;

import java.util.List;

public class Circle extends PhysicsBody implements ITriggerable, ITrigger
{
    @Override
    public void onCollusion(IPhysicsBody body)
    {

    }

    @Override
    public List<physics.Circle> getCircles()
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
    public void trigger()
    {

    }

    @Override
    public boolean addTriggerTarget(ITriggerable target)
    {
        return false;
    }

}
