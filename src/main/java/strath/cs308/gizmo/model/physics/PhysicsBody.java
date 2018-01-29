package strath.cs308.gizmo.model.physics;

import strath.cs308.gizmo.model.interfaces.IPhysicsBody;


public abstract class PhysicsBody implements IPhysicsBody
{
    public abstract void onCollusion(IPhysicsBody collusion);
}
