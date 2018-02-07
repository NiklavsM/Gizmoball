package strath.cs308.gizmo.dycoll.model;

import java.util.List;

public class PhysicalWorld implements IPhysicalWorld
{
    private List<IPhysicalBody> bodies;

    @Override
    public List<IPhysicalBody> getBodies()
    {
        return this.bodies;
    }
}
