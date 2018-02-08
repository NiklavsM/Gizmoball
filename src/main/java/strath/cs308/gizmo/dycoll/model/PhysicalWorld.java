package strath.cs308.gizmo.dycoll.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class PhysicalWorld extends Observable implements IPhysicalWorld
{
    private List<IPhysicalBody> bodies;

    public PhysicalWorld()
    {
        this.bodies = new ArrayList<>();
    }

    @Override
    public List<IPhysicalBody> getBodies()
    {
        return this.bodies;
    }

    public void tick(long time)
    {

    }
}
